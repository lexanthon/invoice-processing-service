package io.github.lexanthon.invoiceparser.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.helger.cii.d16b.CIID16BReader;
import com.helger.ubl21.UBL21Reader;

import io.github.lexanthon.invoiceparser.dao.AllowanceChargeRepo;
import io.github.lexanthon.invoiceparser.dao.BillingReferenceRepo;
import io.github.lexanthon.invoiceparser.dao.BuyerRepo;
import io.github.lexanthon.invoiceparser.dao.DeliveryRepo;
import io.github.lexanthon.invoiceparser.dao.DocumentTotalsRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceLineAllowanceChargeRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceLineItemRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceLinePeriodRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceLinePriceRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceLineRepo;
import io.github.lexanthon.invoiceparser.dao.InvoicePeriodRepo;
import io.github.lexanthon.invoiceparser.dao.InvoiceRepo;
import io.github.lexanthon.invoiceparser.dao.ItemClassificationRepo;
import io.github.lexanthon.invoiceparser.dao.ItemPropertyRepo;
import io.github.lexanthon.invoiceparser.dao.PayeeRepo;
import io.github.lexanthon.invoiceparser.dao.PaymentMeansRepo;
import io.github.lexanthon.invoiceparser.dao.SellerPartyRepo;
import io.github.lexanthon.invoiceparser.dao.SellerRepo;
import io.github.lexanthon.invoiceparser.dao.SellerTaxRepresentativeRepo;
import io.github.lexanthon.invoiceparser.dao.SellerTaxSchemeRepo;
import io.github.lexanthon.invoiceparser.dao.TaxSubtotalRepo;
import io.github.lexanthon.invoiceparser.dao.TaxTotalRepo;
import io.github.lexanthon.invoiceparser.model.AllowanceCharge;
import io.github.lexanthon.invoiceparser.model.BillingReference;
import io.github.lexanthon.invoiceparser.model.Buyer;
import io.github.lexanthon.invoiceparser.model.Delivery;
import io.github.lexanthon.invoiceparser.model.DocumentTotals;
import io.github.lexanthon.invoiceparser.model.Invoice;
import io.github.lexanthon.invoiceparser.model.InvoiceFile;
import io.github.lexanthon.invoiceparser.model.InvoiceLine;
import io.github.lexanthon.invoiceparser.model.InvoiceLineAllowanceCharge;
import io.github.lexanthon.invoiceparser.model.InvoiceLineItem;
import io.github.lexanthon.invoiceparser.model.InvoiceLinePeriod;
import io.github.lexanthon.invoiceparser.model.InvoiceLinePrice;
import io.github.lexanthon.invoiceparser.model.InvoicePeriod;
import io.github.lexanthon.invoiceparser.model.ItemClassification;
import io.github.lexanthon.invoiceparser.model.ItemProperty;
import io.github.lexanthon.invoiceparser.model.Payee;
import io.github.lexanthon.invoiceparser.model.PaymentMeans;
import io.github.lexanthon.invoiceparser.model.Seller;
import io.github.lexanthon.invoiceparser.model.SellerParty;
import io.github.lexanthon.invoiceparser.model.SellerTaxRepresentative;
import io.github.lexanthon.invoiceparser.model.SellerTaxScheme;
import io.github.lexanthon.invoiceparser.model.TaxSubtotal;
import io.github.lexanthon.invoiceparser.model.TaxTotal;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.AllowanceChargeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.BillingReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CommodityClassificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemPropertyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.OrderReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyTaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PaymentMeansType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PaymentTermsType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PeriodType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSubtotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
import un.unece.uncefact.data.standard.crossindustryinvoice._100.CrossIndustryInvoiceType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.LineTradeAgreementType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.ProductCharacteristicType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.ProductClassificationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.ReferencedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.SpecifiedPeriodType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.SupplyChainTradeLineItemType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TaxRegistrationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradeAllowanceChargeType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradePartyType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradeProductType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradeSettlementHeaderMonetarySummationType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradeSettlementPaymentMeansType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100.TradeTaxType;

@Service
public class InvoiceProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceProcessingService.class);

    @Autowired InvoiceRepo invoiceRepo;
    @Autowired BuyerRepo buyerRepo;
    @Autowired SellerRepo sellerRepo;
    @Autowired SellerPartyRepo sellerPartyRepo;
    @Autowired SellerTaxSchemeRepo sellerTaxSchemeRepo;
    @Autowired InvoicePeriodRepo invoicePeriodRepo;
    @Autowired BillingReferenceRepo billingReferenceRepo;
    @Autowired DeliveryRepo deliveryRepo;
    @Autowired PayeeRepo payeeRepo;
    @Autowired SellerTaxRepresentativeRepo sellerTaxRepresentativeRepo;
    @Autowired PaymentMeansRepo paymentMeansRepo;
    @Autowired AllowanceChargeRepo allowanceChargeRepo;
    @Autowired TaxTotalRepo taxTotalRepo;
    @Autowired TaxSubtotalRepo taxSubtotalRepo;
    @Autowired DocumentTotalsRepo documentTotalsRepo;
    @Autowired InvoiceLineRepo invoiceLineRepo;
    @Autowired InvoiceLinePeriodRepo invoiceLinePeriodRepo;
    @Autowired InvoiceLineAllowanceChargeRepo invoiceLineAllowanceChargeRepo;
    @Autowired InvoiceLineItemRepo invoiceLineItemRepo;
    @Autowired InvoiceLinePriceRepo invoiceLinePriceRepo;
    @Autowired ItemClassificationRepo itemClassificationRepo;
    @Autowired ItemPropertyRepo itemPropertyRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void processInvoice(InvoiceFile invoiceFile) throws Exception {
        String xml = invoiceFile.getXml();
        Long invoiceFileRowId = invoiceFile.getId();
        Date dateReceived = invoiceFile.getDateReceived();
        String invoiceType = invoiceFile.getInvoiceType();

        if ("UBL".equals(invoiceType)) {
            parseUBL(xml, invoiceFileRowId, dateReceived);
            return;
        }

        if ("CII".equals(invoiceType)) {
            parseCII(xml, invoiceFileRowId, dateReceived);
            return;
        }

        throw new IllegalArgumentException("Unsupported invoice type: " + invoiceType);
    }

    // ==================================================================================
    // UBL
    // ==================================================================================

    private void parseUBL(String xml, Long invoiceFileRowId, Date dateReceived) throws Exception {
        InvoiceType ubl = UBL21Reader.invoice().read(xml);

        Long invoiceId = persistUblInvoice(ubl, xml, invoiceFileRowId, dateReceived);
        persistUblBuyer(ubl, invoiceId);
        Long sellerId = persistUblSeller(ubl, invoiceId);
        persistUblSellerParties(ubl, sellerId);
        persistUblSellerTaxSchemes(ubl, sellerId);
        persistUblInvoicePeriods(ubl, invoiceId);
        persistUblBillingReferences(ubl, invoiceId);
        persistUblDeliveries(ubl, invoiceId);
        persistUblPayee(ubl, invoiceId);
        persistUblSellerTaxRepresentative(ubl, invoiceId);
        persistUblPaymentMeans(ubl, invoiceId);
        persistUblAllowanceCharges(ubl, invoiceId);
        persistUblTaxTotals(ubl, invoiceId);
        persistUblDocumentTotals(ubl, invoiceId);
        persistUblInvoiceLines(ubl, invoiceId);
    }

    private Long persistUblInvoice(InvoiceType ubl, String xml, Long invoiceFileRowId, Date dateReceived) {
        logger.debug("START INVOICE - UBL");

        String invoiceNumberId = ubl.getIDValue();
        String invoiceNote = ubl.getNoteCount() > 0 ? ubl.getNoteAtIndex(0).getValue() : null;
        Date issueDate = toDate(ubl.getIssueDateValue());
        Date dueDate = toDate(ubl.getDueDateValue());
        String invoiceTypeCode = ubl.getInvoiceTypeCodeValue();
        Date taxPointDate = toDate(ubl.getTaxPointDateValue());

        OrderReferenceType orderRef = ubl.getOrderReference();
        String purchaseOrderRef = orderRef != null ? orderRef.getIDValue() : null;
        String buyerReference = ubl.getBuyerReferenceValue();
        String salesOrderRef = orderRef != null ? orderRef.getSalesOrderIDValue() : null;
        String despatchAdviceReference = ubl.getDespatchDocumentReferenceCount() > 0 ? ubl.getDespatchDocumentReferenceAtIndex(0).getIDValue() : null;
        String receivingDocumentReference = ubl.getReceiptDocumentReferenceCount() > 0 ? ubl.getReceiptDocumentReferenceAtIndex(0).getIDValue() : null;
        String tenderLotReference = ubl.getOriginatorDocumentReferenceCount() > 0 ? ubl.getOriginatorDocumentReferenceAtIndex(0).getIDValue() : null;
        String contractReference = ubl.getContractDocumentReferenceCount() > 0 ? ubl.getContractDocumentReferenceAtIndex(0).getIDValue() : null;
        String projectReference = ubl.getProjectReferenceCount() > 0 ? ubl.getProjectReferenceAtIndex(0).getIDValue() : null;

        PaymentTermsType paymentTerms = ubl.getPaymentTermsCount() > 0 ? ubl.getPaymentTermsAtIndex(0) : null;
        String paymentTermsNote = paymentTerms != null && paymentTerms.getNoteCount() > 0
                ? paymentTerms.getNoteAtIndex(0).getValue()
                : null;

        Invoice invoice = new Invoice(
                invoiceFileRowId,
                xml,
                dateReceived,
                invoiceNumberId,
                issueDate,
                dueDate,
                invoiceTypeCode,
                invoiceNote,
                taxPointDate,
                ubl.getDocumentCurrencyCodeValue(),
                ubl.getTaxCurrencyCodeValue(),
                ubl.getAccountingCostValue(),
                buyerReference,
                purchaseOrderRef,
                salesOrderRef,
                despatchAdviceReference,
                receivingDocumentReference,
                tenderLotReference,
                contractReference,
                projectReference,
                paymentTermsNote
        );

        invoiceRepo.save(invoice);
        logger.debug(invoice.toString());
        logger.debug("END INVOICE - UBL");
        return invoice.getRow_id();
    }

    private void persistUblBuyer(InvoiceType ubl, Long invoiceId) {
        logger.debug("START BUYER - UBL");

        String buyerEndpointId = ubl.getAccountingCustomerParty().getParty().getEndpointIDValue();
        String buyerEndpointSchemeId = ubl.getAccountingCustomerParty().getParty().getEndpointID() != null
                ? ubl.getAccountingCustomerParty().getParty().getEndpointID().getSchemeID()
                : null;
        String buyerPartyId = ubl.getAccountingCustomerParty().getParty().getPartyIdentificationCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPartyIdentificationAtIndex(0).getIDValue()
                : null;
        String partySchemeId = ubl.getAccountingCustomerParty().getParty().getPartyIdentificationCount() > 0
                && ubl.getAccountingCustomerParty().getParty().getPartyIdentificationAtIndex(0).getID() != null
                ? ubl.getAccountingCustomerParty().getParty().getPartyIdentificationAtIndex(0).getID().getSchemeID()
                : null;
        String partyBuyerName = ubl.getAccountingCustomerParty().getParty().getPartyNameCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPartyNameAtIndex(0).getNameValue()
                : null;
        String buyerAddressLine1 = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getStreetNameValue()
                : null;
        String buyerAddressLine2 = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getAdditionalStreetNameValue()
                : null;
        String buyerAddressLine3 = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                && ubl.getAccountingCustomerParty().getParty().getPostalAddress().getAddressLineCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getAddressLineAtIndex(0).getLineValue()
                : null;
        String buyerCity = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getCityNameValue()
                : null;
        String buyerPostCode = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getPostalZoneValue()
                : null;
        String buyerCountrySubdivision = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getCountrySubentityValue()
                : null;
        String buyerCountryCode = ubl.getAccountingCustomerParty().getParty().getPostalAddress() != null
                && ubl.getAccountingCustomerParty().getParty().getPostalAddress().getCountry() != null
                ? ubl.getAccountingCustomerParty().getParty().getPostalAddress().getCountry().getIdentificationCodeValue()
                : null;
        String buyerTaxRegistrationId = ubl.getAccountingCustomerParty().getParty().getPartyTaxSchemeCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPartyTaxSchemeAtIndex(0).getCompanyIDValue()
                : null;
        String buyerTaxSchemeId = ubl.getAccountingCustomerParty().getParty().getPartyTaxSchemeCount() > 0
                && ubl.getAccountingCustomerParty().getParty().getPartyTaxSchemeAtIndex(0).getCompanyID() != null
                ? ubl.getAccountingCustomerParty().getParty().getPartyTaxSchemeAtIndex(0).getCompanyID().getSchemeID()
                : null;
        String buyerLegalName = ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityAtIndex(0).getRegistrationNameValue()
                : null;
        String buyerLegalRegistrationId = ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityCount() > 0
                ? ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyIDValue()
                : null;
        String buyerLegalRegistrationSchemeId = ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityCount() > 0
                && ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyID() != null
                ? ubl.getAccountingCustomerParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyID().getSchemeID()
                : null;
        String buyerContactName = ubl.getAccountingCustomerParty().getParty().getContact() != null
                ? ubl.getAccountingCustomerParty().getParty().getContact().getNameValue()
                : null;
        String buyerContactTelephone = ubl.getAccountingCustomerParty().getParty().getContact() != null
                ? ubl.getAccountingCustomerParty().getParty().getContact().getTelephoneValue()
                : null;
        String buyerContactElectronicMail = ubl.getAccountingCustomerParty().getParty().getContact() != null
                ? ubl.getAccountingCustomerParty().getParty().getContact().getElectronicMailValue()
                : null;

        Buyer buyer = new Buyer(
                invoiceId,
                buyerEndpointId,
                buyerEndpointSchemeId,
                buyerPartyId,
                partySchemeId,
                partyBuyerName,
                buyerAddressLine1,
                buyerAddressLine2,
                buyerAddressLine3,
                buyerCity,
                buyerPostCode,
                buyerCountrySubdivision,
                buyerCountryCode,
                buyerTaxRegistrationId,
                buyerTaxSchemeId,
                buyerLegalName,
                buyerLegalRegistrationId,
                buyerLegalRegistrationSchemeId,
                buyerContactName,
                buyerContactTelephone,
                buyerContactElectronicMail
        );

        buyerRepo.save(buyer);
        logger.debug(buyer.toString());
        logger.debug("END BUYER - UBL");
    }

    private Long persistUblSeller(InvoiceType ubl, Long invoiceId) {
        logger.debug("START SELLER - UBL");

        String sellerEndpointId = ubl.getAccountingSupplierParty().getParty().getEndpointIDValue();
        String sellerEndpointSchemeId = ubl.getAccountingSupplierParty().getParty().getEndpointID() != null
                ? ubl.getAccountingSupplierParty().getParty().getEndpointID().getSchemeID()
                : null;
        String partySellerName = ubl.getAccountingSupplierParty().getParty().getPartyNameCount() > 0
                ? ubl.getAccountingSupplierParty().getParty().getPartyNameAtIndex(0).getNameValue()
                : null;
        String sellerAddressLine1 = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getStreetNameValue()
                : null;
        String sellerAddressLine2 = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getAdditionalStreetNameValue()
                : null;
        String sellerAddressLine3 = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                && ubl.getAccountingSupplierParty().getParty().getPostalAddress().getAddressLineCount() > 0
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getAddressLineAtIndex(0).getLineValue()
                : null;
        String sellerCityName = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getCityNameValue()
                : null;
        String sellerPostCode = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getPostalZoneValue()
                : null;
        String sellerCountrySubdivision = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getCountrySubentityValue()
                : null;
        String sellerCountryCode = ubl.getAccountingSupplierParty().getParty().getPostalAddress() != null
                && ubl.getAccountingSupplierParty().getParty().getPostalAddress().getCountry() != null
                ? ubl.getAccountingSupplierParty().getParty().getPostalAddress().getCountry().getIdentificationCodeValue()
                : null;
        String sellerLegalName = ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityCount() > 0
                ? ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityAtIndex(0).getRegistrationNameValue()
                : null;
        String sellerLegalRegistrationId = ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityCount() > 0
                ? ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyIDValue()
                : null;
        String sellerLegalRegistrationSchemeId = ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityCount() > 0
                && ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyID() != null
                ? ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyID().getSchemeID()
                : null;
        String sellerAdditionalLegalInfo = ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityCount() > 0
                ? ubl.getAccountingSupplierParty().getParty().getPartyLegalEntityAtIndex(0).getCompanyLegalFormCodeValue()
                : null;
        String sellerContactName = ubl.getAccountingSupplierParty().getParty().getContact() != null
                ? ubl.getAccountingSupplierParty().getParty().getContact().getNameValue()
                : null;
        String sellerContactTelephone = ubl.getAccountingSupplierParty().getParty().getContact() != null
                ? ubl.getAccountingSupplierParty().getParty().getContact().getTelephoneValue()
                : null;
        String sellerContactElectronicMail = ubl.getAccountingSupplierParty().getParty().getContact() != null
                ? ubl.getAccountingSupplierParty().getParty().getContact().getElectronicMailValue()
                : null;

        Seller seller = new Seller(
                invoiceId,
                sellerEndpointId,
                sellerEndpointSchemeId,
                partySellerName,
                sellerAddressLine1,
                sellerAddressLine2,
                sellerAddressLine3,
                sellerCityName,
                sellerPostCode,
                sellerCountrySubdivision,
                sellerCountryCode,
                sellerLegalName,
                sellerLegalRegistrationId,
                sellerLegalRegistrationSchemeId,
                sellerAdditionalLegalInfo,
                sellerContactName,
                sellerContactTelephone,
                sellerContactElectronicMail
        );

        sellerRepo.save(seller);
        logger.debug(seller.toString());
        logger.debug("END SELLER - UBL");
        return seller.getRow_id();
    }

    private void persistUblSellerParties(InvoiceType ubl, Long sellerId) {
        if (ubl.getAccountingSupplierParty().getParty().getPartyIdentificationCount() == 0) {
            return;
        }

        logger.debug("START SELLER_PARTY - UBL");
        List<PartyIdentificationType> sellerPartyList = ubl.getAccountingSupplierParty().getParty().getPartyIdentification();
        for (PartyIdentificationType p : sellerPartyList) {
            SellerParty sellerParty = new SellerParty(
                    sellerId,
                    p.getIDValue(),
                    p.getID() != null ? p.getID().getSchemeID() : null
            );
            sellerPartyRepo.save(sellerParty);
            logger.debug(sellerParty.toString());
        }
        logger.debug("END SELLER_PARTY - UBL");
    }

    private void persistUblSellerTaxSchemes(InvoiceType ubl, Long sellerId) {
        if (ubl.getAccountingSupplierParty().getParty().getPartyTaxSchemeCount() == 0) {
            return;
        }

        logger.debug("START SELLER_TAX_SCHEME - UBL");
        List<PartyTaxSchemeType> sellerTaxSchemeList = ubl.getAccountingSupplierParty().getParty().getPartyTaxScheme();
        for (PartyTaxSchemeType p : sellerTaxSchemeList) {
            String sellerTaxNumber = p.getCompanyIDValue();
            String sellerTaxSchemeId = p.getCompanyID() != null ? p.getCompanyID().getSchemeID() : null;

            SellerTaxScheme sellerTaxScheme = new SellerTaxScheme(sellerId, sellerTaxNumber, sellerTaxSchemeId);
            sellerTaxSchemeRepo.save(sellerTaxScheme);
            logger.debug(sellerTaxScheme.toString());
        }
        logger.debug("END SELLER_TAX_SCHEME - UBL");
    }

    private void persistUblInvoicePeriods(InvoiceType ubl, Long invoiceId) {
        if (ubl.getInvoicePeriodCount() == 0) {
            return;
        }

        logger.debug("START INVOICE_PERIOD - UBL");
        List<PeriodType> invoicePeriodList = ubl.getInvoicePeriod();
        for (PeriodType p : invoicePeriodList) {
            Date startDate = toDate(p.getStartDateValue());
            Date endDate = toDate(p.getEndDateValue());
            String taxPointDateCode = p.getDescriptionCodeCount() > 0 ? p.getDescriptionCodeAtIndex(0).getValue() : null;

            InvoicePeriod invoicePeriod = new InvoicePeriod(invoiceId, startDate, endDate, taxPointDateCode);
            invoicePeriodRepo.save(invoicePeriod);
            logger.debug(invoicePeriod.toString());
        }
        logger.debug("END INVOICE_PERIOD - UBL");
    }

    private void persistUblBillingReferences(InvoiceType ubl, Long invoiceId) {
        logger.debug("START BILLING_REFERENCE - UBL");

        if (ubl.getBillingReferenceCount() > 0) {
            List<BillingReferenceType> billingReferenceList = ubl.getBillingReference();
            for (BillingReferenceType b : billingReferenceList) {
                Date precInvoiceIssueDate = b.getInvoiceDocumentReference() != null && b.getInvoiceDocumentReference().getIssueDateValue() != null
                        ? b.getInvoiceDocumentReference().getIssueDateValue().toGregorianCalendar().getTime()
                        : null;
                String precInvoiceNumber = b.getInvoiceDocumentReference() != null
                        ? b.getInvoiceDocumentReference().getIDValue()
                        : null;

                BillingReference billingReference = new BillingReference(invoiceId, precInvoiceNumber, precInvoiceIssueDate);
                billingReferenceRepo.save(billingReference);
                logger.debug(billingReference.toString());
            }
        }

        logger.debug("END BILLING_REFERENCE - UBL");
    }

    private void persistUblDeliveries(InvoiceType ubl, Long invoiceId) {
        if (ubl.getDeliveryCount() == 0) {
            return;
        }

        logger.debug("START DELIVERY - UBL");
        List<DeliveryType> deliveryList = ubl.getDelivery();
        for (DeliveryType d : deliveryList) {
            Date actualDeliveryDate = toDate(d.getActualDeliveryDateValue());
            String locationId = d.getIDValue();
            String locationSchemeId = d.getID() != null ? d.getID().getSchemeID() : null;
            String locationAddress1 = d.getDeliveryAddress() != null ? d.getDeliveryAddress().getStreetNameValue() : null;
            String locationAddress2 = d.getDeliveryAddress() != null ? d.getDeliveryAddress().getAdditionalStreetNameValue() : null;
            String locationAddress3 = d.getDeliveryAddress() != null && d.getDeliveryAddress().getAddressLineCount() > 0
                    ? d.getDeliveryAddress().getAddressLineAtIndex(0).getLineValue()
                    : null;
            String locationCity = d.getDeliveryAddress() != null ? d.getDeliveryAddress().getCityNameValue() : null;
            String locationPostCode = d.getDeliveryAddress() != null ? d.getDeliveryAddress().getPostalZoneValue() : null;
            String locationCountrySubdivision = d.getDeliveryAddress() != null ? d.getDeliveryAddress().getCountrySubentityValue() : null;
            String locationCountryCode = d.getDeliveryAddress() != null && d.getDeliveryAddress().getCountry() != null
                    ? d.getDeliveryAddress().getCountry().getIdentificationCodeValue()
                    : null;
            String deliverToParty = d.getDeliveryParty() != null && d.getDeliveryParty().getPartyNameCount() > 0
                    ? d.getDeliveryParty().getPartyNameAtIndex(0).getNameValue()
                    : null;

            Delivery delivery = new Delivery(
                    invoiceId,
                    actualDeliveryDate,
                    locationId,
                    locationSchemeId,
                    locationAddress1,
                    locationAddress2,
                    locationAddress3,
                    locationCity,
                    locationPostCode,
                    locationCountrySubdivision,
                    locationCountryCode,
                    deliverToParty
            );

            deliveryRepo.save(delivery);
            logger.debug(delivery.toString());
        }
        logger.debug("END DELIVERY - UBL");
    }

    private void persistUblPayee(InvoiceType ubl, Long invoiceId) {
        if (ubl.getPayeeParty() == null) {
            return;
        }

        logger.debug("START PAYEE - UBL");
        String payeePartyId = ubl.getPayeeParty().getPartyIdentificationCount() > 0
                ? ubl.getPayeeParty().getPartyIdentificationAtIndex(0).getIDValue()
                : null;
        String payeeSchemeId = ubl.getPayeeParty().getPartyIdentificationCount() > 0
                && ubl.getPayeeParty().getPartyIdentificationAtIndex(0).getID() != null
                ? ubl.getPayeeParty().getPartyIdentificationAtIndex(0).getID().getSchemeID()
                : null;
        String payeePartyName = ubl.getPayeeParty().getPartyNameCount() > 0
                ? ubl.getPayeeParty().getPartyNameAtIndex(0).getNameValue()
                : null;
        String payeeLegalRegistrationId = ubl.getPayeeParty().getPartyLegalEntityCount() > 0
                ? ubl.getPayeeParty().getPartyLegalEntityAtIndex(0).getCompanyIDValue()
                : null;
        String payeeLegalRegistrationSchemeId = ubl.getPayeeParty().getPartyLegalEntityCount() > 0
                && ubl.getPayeeParty().getPartyLegalEntityAtIndex(0).getCompanyID() != null
                ? ubl.getPayeeParty().getPartyLegalEntityAtIndex(0).getCompanyID().getSchemeID()
                : null;

        Payee payee = new Payee(
                invoiceId,
                payeePartyId,
                payeeSchemeId,
                payeePartyName,
                payeeLegalRegistrationId,
                payeeLegalRegistrationSchemeId
        );

        payeeRepo.save(payee);
        logger.debug(payee.toString());
        logger.debug("END PAYEE - UBL");
    }

    private void persistUblSellerTaxRepresentative(InvoiceType ubl, Long invoiceId) {
        if (ubl.getTaxRepresentativeParty() == null) {
            return;
        }

        logger.debug("START SELLER_TAX_REPRESENTATIVE - UBL");
        String taxRepPartyName = ubl.getTaxRepresentativeParty().getPartyNameCount() > 0
                ? ubl.getTaxRepresentativeParty().getPartyNameAtIndex(0).getNameValue()
                : null;
        String taxRepAddress1 = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getStreetNameValue()
                : null;
        String taxRepAddress2 = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getAdditionalStreetNameValue()
                : null;
        String taxRepAddress3 = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                && ubl.getTaxRepresentativeParty().getPostalAddress().getAddressLineCount() > 0
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getAddressLineAtIndex(0).getLineValue()
                : null;
        String taxRepCity = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getCityNameValue()
                : null;
        String taxRepPostCode = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getPostalZoneValue()
                : null;
        String taxRepCountrySubdivision = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getCountrySubentityCodeValue()
                : null;
        String taxRepCountryCode = ubl.getTaxRepresentativeParty().getPostalAddress() != null
                && ubl.getTaxRepresentativeParty().getPostalAddress().getCountry() != null
                ? ubl.getTaxRepresentativeParty().getPostalAddress().getCountry().getIdentificationCodeValue()
                : null;
        String taxRepRegistrationId = ubl.getTaxRepresentativeParty().getPartyTaxSchemeCount() > 0
                ? ubl.getTaxRepresentativeParty().getPartyTaxSchemeAtIndex(0).getCompanyIDValue()
                : null;
        String taxRepSchemeId = ubl.getTaxRepresentativeParty().getPartyTaxSchemeCount() > 0
                && ubl.getTaxRepresentativeParty().getPartyTaxSchemeAtIndex(0).getCompanyID() != null
                ? ubl.getTaxRepresentativeParty().getPartyTaxSchemeAtIndex(0).getCompanyID().getSchemeID()
                : null;

        SellerTaxRepresentative taxRepresentative = new SellerTaxRepresentative(
                invoiceId,
                taxRepPartyName,
                taxRepAddress1,
                taxRepAddress2,
                taxRepAddress3,
                taxRepCity,
                taxRepPostCode,
                taxRepCountrySubdivision,
                taxRepCountryCode,
                taxRepRegistrationId,
                taxRepSchemeId
        );

        sellerTaxRepresentativeRepo.save(taxRepresentative);
        logger.debug(taxRepresentative.toString());
        logger.debug("END SELLER_TAX_REPRESENTATIVE - UBL");
    }

    private void persistUblPaymentMeans(InvoiceType ubl, Long invoiceId) {
        if (ubl.getPaymentMeansCount() == 0) {
            return;
        }

        logger.debug("START PAYMENT_MEANS - UBL");
        List<PaymentMeansType> paymentMeansList = ubl.getPaymentMeans();
        for (PaymentMeansType p : paymentMeansList) {
            String paymMeansTypeCode = p.getPaymentMeansCodeValue();
            String paymMeansTypeCodeName = p.getPaymentMeansCode() != null ? p.getPaymentMeansCode().getName() : null;
            String paymentInfo = p.getPaymentIDCount() > 0 ? p.getPaymentIDAtIndex(0).getValue() : null;
            String cardAccountNumber = p.getCardAccount() != null ? p.getCardAccount().getPrimaryAccountNumberIDValue() : null;
            String cardNetworkId = p.getCardAccount() != null ? p.getCardAccount().getNetworkIDValue() : null;
            String cardHolderName = p.getCardAccount() != null ? p.getCardAccount().getHolderNameValue() : null;
            String creditAccountId = p.getPayeeFinancialAccount() != null ? p.getPayeeFinancialAccount().getIDValue() : null;
            String creditAccountName = p.getPayeeFinancialAccount() != null ? p.getPayeeFinancialAccount().getNameValue() : null;
            String creditServiceProviderId = p.getPayeeFinancialAccount() != null
                    && p.getPayeeFinancialAccount().getFinancialInstitutionBranch() != null
                    ? p.getPayeeFinancialAccount().getFinancialInstitutionBranch().getIDValue()
                    : null;
            String mandateReferenceId = p.getPaymentMandate() != null ? p.getPaymentMandate().getIDValue() : null;
            String debitedAccountId = p.getPaymentMandate() != null && p.getPaymentMandate().getPayerFinancialAccount() != null
                    ? p.getPaymentMandate().getPayerFinancialAccount().getIDValue()
                    : null;

            PaymentMeans paymentMeans = new PaymentMeans(
                    invoiceId,
                    paymMeansTypeCode,
                    paymMeansTypeCodeName,
                    paymentInfo,
                    cardAccountNumber,
                    cardNetworkId,
                    cardHolderName,
                    creditAccountId,
                    creditAccountName,
                    creditServiceProviderId,
                    mandateReferenceId,
                    debitedAccountId
            );

            paymentMeansRepo.save(paymentMeans);
            logger.debug(paymentMeans.toString());
        }
        logger.debug("END PAYMENT_MEANS - UBL");
    }

    private void persistUblAllowanceCharges(InvoiceType ubl, Long invoiceId) {
        if (ubl.getAllowanceChargeCount() == 0) {
            return;
        }

        logger.debug("START ALLOWANCE_CHARGE - UBL");
        List<AllowanceChargeType> allowanceChargeList = ubl.getAllowanceCharge();
        for (AllowanceChargeType a : allowanceChargeList) {
            Boolean chargeIndicator = a.getChargeIndicator() != null;
            String reasonCode = a.getAllowanceChargeReasonCode() != null ? a.getAllowanceChargeReasonCodeValue() : null;
            String chargeReason = a.getAllowanceChargeReasonCount() > 0 ? a.getAllowanceChargeReasonAtIndex(0).getValue() : null;
            BigDecimal percentage = a.getMultiplierFactorNumericValue();
            BigDecimal amount = a.getAmountValue();
            String amountCurrencyId = a.getAmount() != null ? a.getAmount().getCurrencyID() : null;
            BigDecimal baseAmount = a.getBaseAmountValue();
            String baseAmountCurrencyId = a.getBaseAmount() != null ? a.getBaseAmount().getCurrencyID() : null;
            String vatCategoryCode = a.getTaxCategoryCount() > 0 ? a.getTaxCategoryAtIndex(0).getIDValue() : null;
            BigDecimal vatRate = a.getTaxCategoryCount() > 0 ? a.getTaxCategoryAtIndex(0).getPercentValue() : null;
            String vatSchemeId = a.getTaxCategoryCount() > 0 && a.getTaxCategoryAtIndex(0).getID() != null
                    ? a.getTaxCategoryAtIndex(0).getID().getSchemeID()
                    : null;

            AllowanceCharge allowanceCharge = new AllowanceCharge(
                    invoiceId,
                    chargeIndicator,
                    reasonCode,
                    chargeReason,
                    percentage,
                    amount,
                    amountCurrencyId,
                    baseAmount,
                    baseAmountCurrencyId,
                    vatCategoryCode,
                    vatRate,
                    vatSchemeId
            );
            allowanceChargeRepo.save(allowanceCharge);
            logger.debug(allowanceCharge.toString());
        }
        logger.debug("END ALLOWANCE_CHARGE - UBL");
    }

    private void persistUblTaxTotals(InvoiceType ubl, Long invoiceId) {
        logger.debug("START TAX_TOTAL - UBL");

        List<TaxTotalType> taxTotalList = ubl.getTaxTotal();
        for (TaxTotalType t : taxTotalList) {
            BigDecimal vatAmount = t.getTaxAmountValue();
            String vatAmountCurrencyId = t.getTaxAmount() != null ? t.getTaxAmount().getCurrencyID() : null;

            TaxTotal taxTotal = new TaxTotal(invoiceId, vatAmount, vatAmountCurrencyId);
            taxTotalRepo.save(taxTotal);
            logger.debug(taxTotal.toString());

            Long totalId = taxTotal.getRowId();
            persistUblTaxSubtotals(t, totalId);
        }

        logger.debug("END TAX_TOTAL - UBL");
    }

    private void persistUblTaxSubtotals(TaxTotalType taxTotal, Long totalId) {
        if (taxTotal.getTaxSubtotalCount() == 0) {
            return;
        }

        logger.debug("START TAX_SUBTOTAL - UBL");
        List<TaxSubtotalType> taxSubtotalList = taxTotal.getTaxSubtotal();
        for (TaxSubtotalType t2 : taxSubtotalList) {
            BigDecimal subTaxableAmount = t2.getTaxableAmountValue();
            String subTaxableAmountCurrencyId = t2.getTaxableAmount() != null ? t2.getTaxableAmount().getCurrencyID() : null;
            BigDecimal subTaxAmount = t2.getTaxAmountValue();
            String subTaxAmountCurrencyId = t2.getTaxAmount() != null ? t2.getTaxAmount().getCurrencyID() : null;
            String subCategoryCode = t2.getTaxCategory() != null ? t2.getTaxCategory().getIDValue() : null;
            BigDecimal subCategoryRate = t2.getTaxCategory() != null ? t2.getTaxCategory().getPercentValue() : null;
            String subExemptionReasonCode = t2.getTaxCategory() != null ? t2.getTaxCategory().getTaxExemptionReasonCodeValue() : null;
            String subExemptionReasonText = t2.getTaxCategory() != null && t2.getTaxCategory().getTaxExemptionReasonCount() > 0
                    ? t2.getTaxCategory().getTaxExemptionReasonAtIndex(0).getValue()
                    : null;
            String subTaxSchemeId = t2.getTaxCategory() != null && t2.getTaxCategory().getTaxScheme() != null
                    ? t2.getTaxCategory().getTaxScheme().getIDValue()
                    : null;

            TaxSubtotal taxSubtotal = new TaxSubtotal(
                    totalId,
                    subTaxableAmount,
                    subTaxableAmountCurrencyId,
                    subTaxAmount,
                    subTaxAmountCurrencyId,
                    subCategoryCode,
                    subCategoryRate,
                    subExemptionReasonCode,
                    subExemptionReasonText,
                    subTaxSchemeId
            );
            taxSubtotalRepo.save(taxSubtotal);
            logger.debug(taxSubtotal.toString());
        }
        logger.debug("END TAX_SUBTOTAL - UBL");
    }

    private void persistUblDocumentTotals(InvoiceType ubl, Long invoiceId) {
        if (ubl.getLegalMonetaryTotal() == null) {
            return;
        }

        logger.debug("START DOCUMENT_TOTALS - UBL");

        DocumentTotals documentTotals = new DocumentTotals(
                invoiceId,
                ubl.getLegalMonetaryTotal().getLineExtensionAmountValue(),
                ubl.getLegalMonetaryTotal().getLineExtensionAmount() != null ? ubl.getLegalMonetaryTotal().getLineExtensionAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getTaxExclusiveAmountValue(),
                ubl.getLegalMonetaryTotal().getTaxExclusiveAmount() != null ? ubl.getLegalMonetaryTotal().getTaxExclusiveAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getTaxInclusiveAmountValue(),
                ubl.getLegalMonetaryTotal().getTaxInclusiveAmount() != null ? ubl.getLegalMonetaryTotal().getTaxInclusiveAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getAllowanceTotalAmountValue(),
                ubl.getLegalMonetaryTotal().getAllowanceTotalAmount() != null ? ubl.getLegalMonetaryTotal().getAllowanceTotalAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getChargeTotalAmountValue(),
                ubl.getLegalMonetaryTotal().getChargeTotalAmount() != null ? ubl.getLegalMonetaryTotal().getChargeTotalAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getPrepaidAmountValue(),
                ubl.getLegalMonetaryTotal().getPrepaidAmount() != null ? ubl.getLegalMonetaryTotal().getPrepaidAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getPayableRoundingAmountValue(),
                ubl.getLegalMonetaryTotal().getPayableRoundingAmount() != null ? ubl.getLegalMonetaryTotal().getPayableRoundingAmount().getCurrencyID() : null,
                ubl.getLegalMonetaryTotal().getPayableAmountValue(),
                ubl.getLegalMonetaryTotal().getPayableAmount() != null ? ubl.getLegalMonetaryTotal().getPayableAmount().getCurrencyID() : null
        );

        documentTotalsRepo.save(documentTotals);
        logger.debug(documentTotals.toString());
        logger.debug("END DOCUMENT_TOTALS - UBL");
    }

    private void persistUblInvoiceLines(InvoiceType ubl, Long invoiceId) {
        logger.debug("START INVOICE_LINE - UBL");

        List<InvoiceLineType> invoiceLineList = ubl.getInvoiceLine();
        for (InvoiceLineType i : invoiceLineList) {
            InvoiceLine invoiceLine = new InvoiceLine(
                    invoiceId,
                    i.getIDValue(),
                    i.getNoteCount() > 0 ? i.getNoteAtIndex(0).getValue() : null,
                    i.getInvoicedQuantityValue(),
                    i.getInvoicedQuantity() != null ? i.getInvoicedQuantity().getUnitCode() : null,
                    i.getLineExtensionAmountValue(),
                    i.getLineExtensionAmount() != null ? i.getLineExtensionAmount().getCurrencyID() : null,
                    i.getAccountingCostValue(),
                    i.getOrderLineReferenceCount() > 0 ? i.getOrderLineReferenceAtIndex(0).getLineIDValue() : null,
                    i.getDocumentReferenceCount() > 0 ? i.getDocumentReferenceAtIndex(0).getIDValue() : null,
                    i.getDocumentReferenceCount() > 0 && i.getDocumentReferenceAtIndex(0).getID() != null
                            ? i.getDocumentReferenceAtIndex(0).getID().getSchemeID()
                            : null,
                    i.getDocumentReferenceCount() > 0 ? i.getDocumentReferenceAtIndex(0).getDocumentTypeCodeValue() : null
            );

            invoiceLineRepo.save(invoiceLine);
            logger.debug(invoiceLine.toString());

            Long lineId = invoiceLine.getRowId();
            persistUblInvoiceLinePeriod(i, lineId);
            persistUblInvoiceLineAllowanceCharges(i, lineId);
            persistUblInvoiceLinePrice(i, lineId);
            persistUblInvoiceLineItem(i, lineId);
        }

        logger.debug("END INVOICE_LINE - UBL");
    }

    private void persistUblInvoiceLinePeriod(InvoiceLineType line, Long lineId) {
        if (line.getInvoicePeriodCount() == 0) {
            return;
        }

        logger.debug("START INVOICE_PERIOD_COUNT - UBL");
        Date invPeriodStartDate = line.getInvoicePeriodAtIndex(0).getStartDateValue() != null
                ? line.getInvoicePeriodAtIndex(0).getStartDateValue().toGregorianCalendar().getTime()
                : null;
        Date invPeriodEndDate = line.getInvoicePeriodAtIndex(0).getEndDateValue() != null
                ? line.getInvoicePeriodAtIndex(0).getEndDateValue().toGregorianCalendar().getTime()
                : null;
        String taxPointDateCode = line.getInvoicePeriodAtIndex(0).getDescriptionCodeCount() > 0
                ? line.getInvoicePeriodAtIndex(0).getDescriptionCodeAtIndex(0).getValue()
                : null;

        InvoiceLinePeriod invoiceLinePeriod = new InvoiceLinePeriod(lineId, invPeriodStartDate, invPeriodEndDate, taxPointDateCode);
        invoiceLinePeriodRepo.save(invoiceLinePeriod);
        logger.debug(invoiceLinePeriod.toString());
        logger.debug("END INVOICE_PERIOD_COUNT - UBL");
    }

    private void persistUblInvoiceLineAllowanceCharges(InvoiceLineType line, Long lineId) {
        if (line.getAllowanceChargeCount() == 0) {
            return;
        }

        logger.debug("START INVOICE_LINE_ALLOWANCE_CHARGE - UBL");
        List<AllowanceChargeType> invoiceLineAllowanceChargeList = line.getAllowanceCharge();
        for (AllowanceChargeType a : invoiceLineAllowanceChargeList) {
            Boolean chargeIndicator = a.getChargeIndicator() != null;
            String reasonCode = a.getAllowanceChargeReasonCodeValue();
            String reason = a.getAllowanceChargeReasonCount() > 0 ? a.getAllowanceChargeReasonAtIndex(0).getValue() : null;
            BigDecimal percentage = a.getMultiplierFactorNumericValue();
            BigDecimal amount = a.getAmountValue();
            String amountCurrencyId = a.getAmount() != null ? a.getAmount().getCurrencyID() : null;
            BigDecimal baseAmount = a.getBaseAmountValue();
            String baseAmountCurrencyId = a.getBaseAmount() != null ? a.getBaseAmount().getCurrencyID() : null;

            InvoiceLineAllowanceCharge invoiceLineAllowanceCharge = new InvoiceLineAllowanceCharge(
                    lineId,
                    chargeIndicator,
                    reasonCode,
                    reason,
                    percentage,
                    amount,
                    amountCurrencyId,
                    baseAmount,
                    baseAmountCurrencyId
            );
            invoiceLineAllowanceChargeRepo.save(invoiceLineAllowanceCharge);
            logger.debug(invoiceLineAllowanceCharge.toString());
        }
        logger.debug("END INVOICE_LINE_ALLOWANCE_CHARGE - UBL");
    }

    private void persistUblInvoiceLinePrice(InvoiceLineType line, Long lineId) {
        if (line.getPrice() == null) {
            return;
        }

        logger.debug("START INVOICE_LINE_PRICE - UBL");
        BigDecimal priceNetAmount = line.getPrice().getPriceAmountValue();
        String priceNetAmountCurrencyId = line.getPrice().getPriceAmount() != null ? line.getPrice().getPriceAmount().getCurrencyID() : null;
        BigDecimal priceBaseQuantity = line.getPrice().getBaseQuantityValue();
        String priceBaseQuantityUnitCode = line.getPrice().getBaseQuantity() != null && line.getPrice().getBaseQuantity().getUnitCode() != null
                ? line.getPrice().getBaseQuantity().getUnitCode()
                : null;
        Boolean priceChargeIndicator = line.getPrice().getAllowanceChargeCount() > 0 && line.getPrice().getAllowanceChargeAtIndex(0).getChargeIndicator() != null
                ? line.getPrice().getAllowanceChargeAtIndex(0).getChargeIndicator() != null
                : null;
        BigDecimal priceDiscount = line.getPrice().getAllowanceChargeCount() > 0 ? line.getPrice().getAllowanceChargeAtIndex(0).getAmountValue() : null;
        String priceDiscountCurrencyId = line.getPrice().getAllowanceChargeCount() > 0 && line.getPrice().getAllowanceChargeAtIndex(0).getAmount() != null
                ? line.getPrice().getAllowanceChargeAtIndex(0).getAmount().getCurrencyID()
                : null;
        BigDecimal grossPrice = line.getPrice().getAllowanceChargeCount() > 0 ? line.getPrice().getAllowanceChargeAtIndex(0).getBaseAmountValue() : null;
        String grossPriceCurrencyId = line.getPrice().getAllowanceChargeCount() > 0 && line.getPrice().getAllowanceChargeAtIndex(0).getBaseAmount() != null
                ? line.getPrice().getAllowanceChargeAtIndex(0).getBaseAmount().getCurrencyID()
                : null;

        InvoiceLinePrice invoiceLinePrice = new InvoiceLinePrice(
                lineId,
                priceNetAmount,
                priceNetAmountCurrencyId,
                priceBaseQuantity,
                priceBaseQuantityUnitCode,
                priceChargeIndicator,
                priceDiscount,
                priceDiscountCurrencyId,
                grossPrice,
                grossPriceCurrencyId
        );
        invoiceLinePriceRepo.save(invoiceLinePrice);
        logger.debug(invoiceLinePrice.toString());
        logger.debug("END INVOICE_LINE_PRICE - UBL");
    }

    private void persistUblInvoiceLineItem(InvoiceLineType line, Long lineId) {
        if (line.getItem() == null) {
            return;
        }

        logger.debug("START INVOICE_LINE_ITEM - UBL");
        String itemDescription = line.getItem().getDescriptionCount() > 0 ? line.getItem().getDescriptionAtIndex(0).getValue() : null;
        String itemName = line.getItem().getNameValue();
        String itemBuyersId = line.getItem().getBuyersItemIdentification() != null ? line.getItem().getBuyersItemIdentification().getIDValue() : null;
        String itemSellersId = line.getItem().getSellersItemIdentification() != null ? line.getItem().getSellersItemIdentification().getIDValue() : null;
        String itemStandardId = line.getItem().getStandardItemIdentification() != null ? line.getItem().getStandardItemIdentification().getIDValue() : null;
        String itemStandardSchemeId = line.getItem().getStandardItemIdentification() != null && line.getItem().getStandardItemIdentification().getID() != null
                ? line.getItem().getStandardItemIdentification().getID().getSchemeID()
                : null;
        String itemOriginCountry = line.getItem().getOriginCountry() != null ? line.getItem().getOriginCountry().getIdentificationCodeValue() : null;
        String itemVatCategoryCode = line.getItem().getClassifiedTaxCategoryCount() > 0 ? line.getItem().getClassifiedTaxCategoryAtIndex(0).getIDValue() : null;
        BigDecimal itemVatRate = line.getItem().getClassifiedTaxCategoryCount() > 0 ? line.getItem().getClassifiedTaxCategoryAtIndex(0).getPercentValue() : null;
        String itemTaxSchemeId = line.getItem().getClassifiedTaxCategoryCount() > 0 && line.getItem().getClassifiedTaxCategoryAtIndex(0).getID() != null
                ? line.getItem().getClassifiedTaxCategoryAtIndex(0).getID().getSchemeID()
                : null;

        InvoiceLineItem invoiceLineItem = new InvoiceLineItem(
                lineId,
                itemDescription,
                itemName,
                itemBuyersId,
                itemSellersId,
                itemStandardId,
                itemStandardSchemeId,
                itemOriginCountry,
                itemVatCategoryCode,
                itemVatRate,
                itemTaxSchemeId
        );

        invoiceLineItemRepo.save(invoiceLineItem);
        logger.debug(invoiceLineItem.toString());

        Long itemId = invoiceLineItem.getRowId();
        persistUblItemClassifications(line, itemId);
        persistUblItemProperties(line, itemId);

        logger.debug("END INVOICE_LINE_ITEM - UBL");
    }

    private void persistUblItemClassifications(InvoiceLineType line, Long itemId) {
        if (line.getItem().getCommodityClassificationCount() == 0) {
            return;
        }

        logger.debug("START ITEM_CLASSIFICATION - UBL");
        List<CommodityClassificationType> itemClassList = line.getItem().getCommodityClassification();
        for (CommodityClassificationType c : itemClassList) {
            String itemClassId = c.getItemClassificationCodeValue();
            String itemClassSchemeId = c.getItemClassificationCode() != null ? c.getItemClassificationCode().getListID() : null;
            String itemClassSchemeVersionId = c.getItemClassificationCode() != null ? c.getItemClassificationCode().getListVersionID() : null;

            ItemClassification itemClassification = new ItemClassification(itemId, itemClassId, itemClassSchemeId, itemClassSchemeVersionId);
            itemClassificationRepo.save(itemClassification);
            logger.debug(itemClassification.toString());
        }
        logger.debug("END ITEM_CLASSIFICATION - UBL");
    }

    private void persistUblItemProperties(InvoiceLineType line, Long itemId) {
        if (line.getItem().getAdditionalItemPropertyCount() == 0) {
            return;
        }

        logger.debug("START ITEM_PROPERTY - UBL");
        List<ItemPropertyType> itemPropertyList = line.getItem().getAdditionalItemProperty();
        for (ItemPropertyType ip : itemPropertyList) {
            String propName = ip.getNameValue();
            String propValue = ip.getValueValue();

            ItemProperty itemProperty = new ItemProperty(itemId, propName, propValue);
            itemPropertyRepo.save(itemProperty);
            logger.debug(itemProperty.toString());
        }
        logger.debug("END ITEM_PROPERTY - UBL");
    }

    // ==================================================================================
    // CII
    // ==================================================================================

    private void parseCII(String xml, Long invoiceFileRowId, Date dateReceived) throws Exception {
        CrossIndustryInvoiceType aCIIObject = CIID16BReader.crossIndustryInvoice().read(xml);

        logger.debug("START INVOICE - CII");
        String invoiceNumberId = aCIIObject.getExchangedDocument() != null && aCIIObject.getExchangedDocument().getID() != null ? aCIIObject.getExchangedDocument().getID().getValue() : null;
        Date issueDate = aCIIObject.getExchangedDocument() != null && aCIIObject.getExchangedDocument().getIssueDateTime() != null &&
                aCIIObject.getExchangedDocument().getIssueDateTime().getDateTime() != null ? aCIIObject.getExchangedDocument().getIssueDateTime().getDateTime().toGregorianCalendar().getTime() : null;
        Date dueDate = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsCount() > 0 &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDueDateDateTime() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDueDateDateTime().getDateTime() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDueDateDateTime().getDateTime().toGregorianCalendar().getTime() : null;
        String invoiceTypeCode = aCIIObject.getExchangedDocument() != null && aCIIObject.getExchangedDocument().getTypeCode() != null ?
                aCIIObject.getExchangedDocument().getTypeCode().getValue() : null;
        String invoiceNote = aCIIObject.getExchangedDocument() != null && aCIIObject.getExchangedDocument().getIncludedNoteCount() > 0 &&
                aCIIObject.getExchangedDocument().getIncludedNoteAtIndex(0).getContentCount() > 0 ? aCIIObject.getExchangedDocument().getIncludedNoteAtIndex(0).getContentAtIndex(0).getValue() : null;
        Date taxPointDate = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTaxCount() > 0 &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTaxAtIndex(0).getTaxPointDate() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTaxAtIndex(0).getTaxPointDate().getDate() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTaxAtIndex(0).getTaxPointDate().getDate().toGregorianCalendar().getTime() : null;
        String invoiceCurrencyCode = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getInvoiceCurrencyCode() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getInvoiceCurrencyCode().getValue() : null;
        String invoiceTaxCurrencyCode = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getTaxCurrencyCode() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getTaxCurrencyCode().getValue() : null;
        String invoiceBuyerAccReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getReceivableSpecifiedTradeAccountingAccountCount() > 0 &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getReceivableSpecifiedTradeAccountingAccountAtIndex(0).getID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getReceivableSpecifiedTradeAccountingAccountAtIndex(0).getID().getValue() : null;
        String buyerReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerReference() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerReference().getValue() : null;
        String purchaseOrderRef = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerOrderReferencedDocument() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerOrderReferencedDocument().getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerOrderReferencedDocument().getIssuerAssignedID().getValue() : null;
        String salesOrderRef = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerOrderReferencedDocument() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerOrderReferencedDocument().getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerOrderReferencedDocument().getIssuerAssignedID().getValue() : null;
        String despatchAdviceReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getDespatchAdviceReferencedDocument() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getDespatchAdviceReferencedDocument().getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getDespatchAdviceReferencedDocument().getIssuerAssignedID().getValue() : null;
        String receivingDocumentReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getReceivingAdviceReferencedDocument() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getReceivingAdviceReferencedDocument().getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getReceivingAdviceReferencedDocument().getIssuerAssignedID().getValue() : null;
        String tenderLotReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getAdditionalReferencedDocumentCount() > 0 &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID().getValue() : null;
        String contractReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getContractReferencedDocument() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getContractReferencedDocument().getIssuerAssignedID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getContractReferencedDocument().getIssuerAssignedID().getValue() : null;
        String projectReference = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSpecifiedProcuringProject() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSpecifiedProcuringProject().getID() != null ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSpecifiedProcuringProject().getID().getValue() : null;
        String paymentTermsNote = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsCount() > 0 &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDescriptionCount() > 0 ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDescriptionAtIndex(0).getValue() : null;

        Invoice myInvoice = new Invoice(invoiceFileRowId, xml, dateReceived, invoiceNumberId, issueDate, dueDate, invoiceTypeCode, invoiceNote, taxPointDate, invoiceCurrencyCode,
                invoiceTaxCurrencyCode, invoiceBuyerAccReference, buyerReference, purchaseOrderRef, salesOrderRef,
                despatchAdviceReference, receivingDocumentReference, tenderLotReference, contractReference, projectReference, paymentTermsNote);

        invoiceRepo.save(myInvoice);
        logger.debug(myInvoice.toString());
        Long invoiceId = myInvoice.getRow_id();
        logger.debug("END INVOICE - CII");

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerTradeParty() != null) {
            TradePartyType tpt = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getBuyerTradeParty();
            logger.debug("START BUYER - CII");

            String buyerEndpointId = tpt.getURIUniversalCommunicationCount() > 0 && tpt.getURIUniversalCommunicationAtIndex(0).getURIID() != null ?
                    tpt.getURIUniversalCommunicationAtIndex(0).getURIID().getValue() : null;
            String buyerEndpointSchemeId = tpt.getURIUniversalCommunicationCount() > 0 && tpt.getURIUniversalCommunicationAtIndex(0).getURIID() != null &&
                    tpt.getURIUniversalCommunicationAtIndex(0).getURIID().getSchemeID() != null ? tpt.getURIUniversalCommunicationAtIndex(0).getURIID().getSchemeID() : null;
            String buyerPartyId = tpt.getIDCount() > 0 ? tpt.getIDAtIndex(0).getValue() : null;
            String partySchemeId = tpt.getIDCount() > 0 && tpt.getIDAtIndex(0).getSchemeID() != null ? tpt.getIDAtIndex(0).getSchemeID() : null;
            String partyBuyerName = tpt.getName() != null ? tpt.getName().getValue() : null;
            String buyerAddressLine1 = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getStreetName() != null ? tpt.getPostalTradeAddress().getStreetName().getValue() : null;
            String buyerAddressLine2 = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getAdditionalStreetName() != null ? tpt.getPostalTradeAddress().getAdditionalStreetName().getValue() : null;
            String buyerAddressLine3 = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getLineThree() != null ? tpt.getPostalTradeAddress().getLineThree().getValue() : null;
            String buyerCity = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getCityName() != null ? tpt.getPostalTradeAddress().getCityName().getValue() : null;
            String buyerPostCode = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getPostcodeCode() != null ? tpt.getPostalTradeAddress().getPostcodeCode().getValue() : null;
            String buyerCountrySubdivision = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getCountrySubDivisionNameCount() > 0 ? tpt.getPostalTradeAddress().getCountrySubDivisionNameAtIndex(0).getValue() : null;
            String buyerCountryCode = tpt.getPostalTradeAddress() != null && tpt.getPostalTradeAddress().getCountryID() != null ? tpt.getPostalTradeAddress().getCountryID().getValue() : null;
            String taxRegistrationId = tpt.getSpecifiedTaxRegistrationCount() > 0 && tpt.getSpecifiedTaxRegistrationAtIndex(0).getID() != null ?
                    tpt.getSpecifiedTaxRegistrationAtIndex(0).getID().getValue() : null;
            String buyerTaxSchemeId = tpt.getSpecifiedTaxRegistrationCount() > 0 && tpt.getSpecifiedTaxRegistrationAtIndex(0).getID() != null &&
                    tpt.getSpecifiedTaxRegistrationAtIndex(0).getID().getSchemeID() != null ? tpt.getSpecifiedTaxRegistrationAtIndex(0).getID().getSchemeID() : null;
            String buyerLegalName = tpt.getSpecifiedLegalOrganization() != null && tpt.getSpecifiedLegalOrganization().getTradingBusinessName() != null ?
                    tpt.getSpecifiedLegalOrganization().getTradingBusinessName().getValue() : null;
            String buyerLegalRegistrationId = tpt.getSpecifiedLegalOrganization() != null && tpt.getSpecifiedLegalOrganization().getID() != null ?
                    tpt.getSpecifiedLegalOrganization().getID().getValue() : null;
            String buyerLegalRegistrationSchemeId = tpt.getSpecifiedLegalOrganization() != null && tpt.getSpecifiedLegalOrganization().getID() != null &&
                    tpt.getSpecifiedLegalOrganization().getID().getSchemeID() != null ? tpt.getSpecifiedLegalOrganization().getID().getSchemeID() : null;
            String buyerContactName = tpt.getDefinedTradeContactCount() > 0 && tpt.getDefinedTradeContactAtIndex(0).getPersonName() != null ?
                    tpt.getDefinedTradeContactAtIndex(0).getPersonName().getValue() : null;
            String buyerContactTelephone = tpt.getDefinedTradeContactCount() > 0 && tpt.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication() != null &&
                    tpt.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication().getCompleteNumber() != null ? tpt.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication().getCompleteNumber().getValue() : null;
            String buyerContactElectronicMail = tpt.getDefinedTradeContactCount() > 0 && tpt.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication() != null &&
                    tpt.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication().getURIID() != null ?
                    tpt.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication().getURIID().getValue() : null;

            Buyer theBuyer = new Buyer(invoiceId, buyerEndpointId, buyerEndpointSchemeId, buyerPartyId, partySchemeId, partyBuyerName, buyerAddressLine1, buyerAddressLine2,
                    buyerAddressLine3, buyerCity, buyerPostCode, buyerCountrySubdivision, buyerCountryCode, taxRegistrationId, buyerTaxSchemeId, buyerLegalName, buyerLegalRegistrationId,
                    buyerLegalRegistrationSchemeId, buyerContactName, buyerContactTelephone, buyerContactElectronicMail);

            buyerRepo.save(theBuyer);
            logger.debug(theBuyer.toString());
            logger.debug("END BUYER - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty() != null) {
            TradePartyType tpt2 = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty();

            logger.debug("START SELLER - CII");
            String sellerEndpointId = tpt2.getGlobalIDCount() > 0 ? tpt2.getGlobalIDAtIndex(0).getValue() : null;
            String sellerEndpointSchemeId = tpt2.getGlobalIDCount() > 0 && tpt2.getGlobalIDAtIndex(0).getSchemeID() != null ? tpt2.getGlobalIDAtIndex(0).getSchemeID() : null;
            String partySellerName = tpt2.getName().getValue();
            String sellerAddressLine1 = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getStreetName() != null ? tpt2.getPostalTradeAddress().getStreetName().getValue() : null;
            String sellerAddressLine2 = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getAdditionalStreetName() != null ? tpt2.getPostalTradeAddress().getAdditionalStreetName().getValue() : null;
            String sellerAddressLine3 = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getLineThree() != null ? tpt2.getPostalTradeAddress().getLineThree().getValue() : null;
            String sellerCityName = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getCityName() != null ? tpt2.getPostalTradeAddress().getCityName().getValue() : null;
            String sellerPostCode = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getPostcodeCode() != null ? tpt2.getPostalTradeAddress().getPostcodeCode().getValue() : null;
            String sellerCountrySubdivision = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getCountrySubDivisionNameCount() > 0 ? tpt2.getPostalTradeAddress().getCountrySubDivisionNameAtIndex(0).getValue() : null;
            String sellerCountryCode = tpt2.getPostalTradeAddress() != null && tpt2.getPostalTradeAddress().getCountryID() != null ? tpt2.getPostalTradeAddress().getCountryID().getValue() : null;
            String sellerLegalName = tpt2.getSpecifiedLegalOrganization() != null && tpt2.getSpecifiedLegalOrganization().getTradingBusinessName() != null ?
                    tpt2.getSpecifiedLegalOrganization().getTradingBusinessName().getValue() : null;
            String sellerLegalRegistrationId = tpt2.getSpecifiedLegalOrganization() != null && tpt2.getSpecifiedLegalOrganization().getID() != null ?
                    tpt2.getSpecifiedLegalOrganization().getID().getValue() : null;
            String sellerLegalRegistrationSchemeId = tpt2.getSpecifiedLegalOrganization() != null && tpt2.getSpecifiedLegalOrganization().getID() != null &&
                    tpt2.getSpecifiedLegalOrganization().getID().getSchemeID() != null ? tpt2.getSpecifiedLegalOrganization().getID().getSchemeID() : null;
            String sellerAdditionalLegalInfo = tpt2.getDescriptionCount() > 0 ? tpt2.getDescriptionAtIndex(0).getValue() : null;
            String sellerContactName = tpt2.getDefinedTradeContactCount() > 0 && tpt2.getDefinedTradeContactAtIndex(0).getPersonName() != null ?
                    tpt2.getDefinedTradeContactAtIndex(0).getPersonName().getValue() : null;
            String sellerContactTelephone = tpt2.getDefinedTradeContactCount() > 0 && tpt2.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication() != null &&
                    tpt2.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication().getCompleteNumber() != null ?
                    tpt2.getDefinedTradeContactAtIndex(0).getTelephoneUniversalCommunication().getCompleteNumber().getValue() : null;
            String sellerContactElectronicMail = tpt2.getDefinedTradeContactCount() > 0 && tpt2.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication() != null &&
                    tpt2.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication().getURIID() != null ?
                    tpt2.getDefinedTradeContactAtIndex(0).getEmailURIUniversalCommunication().getURIID().getValue() : null;

            Seller theSeller = new Seller(invoiceId, sellerEndpointId, sellerEndpointSchemeId, partySellerName, sellerAddressLine1, sellerAddressLine2, sellerAddressLine3,
                    sellerCityName, sellerPostCode, sellerCountrySubdivision, sellerCountryCode, sellerLegalName, sellerLegalRegistrationId, sellerLegalRegistrationSchemeId,
                    sellerAdditionalLegalInfo, sellerContactName, sellerContactTelephone, sellerContactElectronicMail);

            sellerRepo.save(theSeller);
            logger.debug(theSeller.toString());
            Long sellerId = theSeller.getRow_id();
            logger.debug("END SELLER - CII");

            if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty() != null) {
                TradePartyType sp = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty();
                logger.debug("START SELLER_PARTY - CII");
                String sellerPartyIdentifier = sp.getIDCount() > 0 ? sp.getIDAtIndex(0).getValue() : null;
                String sellerPartySchemeId = sp.getIDCount() > 0 ? sp.getIDAtIndex(0).getSchemeID() : null;
                SellerParty theSellerParty = new SellerParty(sellerId, sellerPartyIdentifier, sellerPartySchemeId);
                sellerPartyRepo.save(theSellerParty);
                logger.debug(theSellerParty.toString());
                logger.debug("END SELLER_PARTY - CII");
            }

            if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty() != null) {
                logger.debug("START SELLER_TAX_SCHEME - CII");
                List<TaxRegistrationType> sellerTaxSchemeList = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTradeParty().getSpecifiedTaxRegistration();
                for (TaxRegistrationType p : sellerTaxSchemeList) {
                    String sellerTaxNumber = p.getID() != null ? p.getID().getValue() : null;
                    String sellerTaxSchemeId = p.getID() != null ? p.getID().getSchemeID() : null;
                    SellerTaxScheme theSellerTaxScheme = new SellerTaxScheme(sellerId, sellerTaxNumber, sellerTaxSchemeId);
                    sellerTaxSchemeRepo.save(theSellerTaxScheme);
                    logger.debug(theSellerTaxScheme.toString());
                }
                logger.debug("END SELLER_TAX_SCHEME - CII");
            }
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getBillingSpecifiedPeriod() != null) {
            logger.debug("START INVOICE_PERIOD - CII");
            SpecifiedPeriodType ip = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getBillingSpecifiedPeriod();
            Date startDate = ip.getStartDateTime() != null && ip.getStartDateTime().getDateTime() != null ? ip.getStartDateTime().getDateTime().toGregorianCalendar().getTime() : null;
            Date endDate = ip.getEndDateTime() != null && ip.getEndDateTime().getDateTime() != null ? ip.getEndDateTime().getDateTime().toGregorianCalendar().getTime() : null;
            String taxPointDateCode = null;

            InvoicePeriod theInvoicePeriod = new InvoicePeriod(invoiceId, startDate, endDate, taxPointDateCode);
            invoicePeriodRepo.save(theInvoicePeriod);
            logger.debug(theInvoicePeriod.toString());

            logger.debug("END INVOICE_PERIOD - CII");
        }

        logger.debug("START BILLING_REFERENCE - CII");
        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getInvoiceReferencedDocument() != null) {
            ReferencedDocumentType br = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getInvoiceReferencedDocument();
            String precInvoiceNumber = br.getIssuerAssignedID() != null ? br.getIssuerAssignedID().getValue() : null;
            Date precInvoiceIssueDate = br.getFormattedIssueDateTime() != null && br.getFormattedIssueDateTime().getDateTimeString() != null ? null : null;

            BillingReference theBillingReference = new BillingReference(invoiceId, precInvoiceNumber, precInvoiceIssueDate);
            billingReferenceRepo.save(theBillingReference);
            logger.debug(theBillingReference.toString());

            logger.debug("END BILLING_REFERENCE - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getShipToTradeParty() != null) {
            logger.debug("START DELIVERY - CII");
            TradePartyType d = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getShipToTradeParty();

            Date actualDeliveryDate = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery() != null &&
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getActualDeliverySupplyChainEvent() != null &&
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getActualDeliverySupplyChainEvent().getOccurrenceDateTime() != null ?
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeDelivery().getActualDeliverySupplyChainEvent().getOccurrenceDateTime().getDateTime().toGregorianCalendar().getTime() : null;
            String locationId = d.getIDCount() > 0 ? d.getIDAtIndex(0).getValue() : null;
            String locationSchemeId = d.getIDCount() > 0 && d.getIDAtIndex(0).getSchemeID() != null ? d.getIDAtIndex(0).getSchemeID() : null;
            String locationAddress1 = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getLineOne() != null ? d.getPostalTradeAddress().getLineOne().getValue() : null;
            String locationAddress2 = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getLineTwo() != null ? d.getPostalTradeAddress().getLineTwo().getValue() : null;
            String locationAddress3 = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getLineThree() != null ? d.getPostalTradeAddress().getLineThree().getValue() : null;
            String locationCity = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getCityName() != null ? d.getPostalTradeAddress().getCityName().getValue() : null;
            String locationPostCode = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getPostcodeCode() != null ? d.getPostalTradeAddress().getPostcodeCode().getValue() : null;
            String locationCountrySubdivision = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getCountrySubDivisionNameCount() > 0 ?
                    d.getPostalTradeAddress().getCountrySubDivisionNameAtIndex(0).getValue() : null;
            String locationCountryCode = d.getPostalTradeAddress() != null && d.getPostalTradeAddress().getCountryID() != null ? d.getPostalTradeAddress().getCountryID().getValue() : null;
            String deliverToParty = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty() != null &&
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty().getSpecifiedLegalOrganization() != null &&
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty().getSpecifiedLegalOrganization().getTradingBusinessName() != null ?
                    aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty().getSpecifiedLegalOrganization().getTradingBusinessName().getValue() : null;

            Delivery theDelivery = new Delivery(invoiceId, actualDeliveryDate, locationId, locationSchemeId, locationAddress1, locationAddress2, locationAddress3, locationCity, locationPostCode, locationCountrySubdivision, locationCountryCode, deliverToParty);
            deliveryRepo.save(theDelivery);
            logger.debug(theDelivery.toString());

            logger.debug("END DELIVERY - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getPayeeTradeParty() != null) {
            TradePartyType ptp = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getPayeeTradeParty();
            logger.debug("START PAYEE - CII");
            String payeePartyId = ptp.getIDCount() > 0 ? ptp.getIDAtIndex(0).getValue() : null;
            String payeeSchemeId = ptp.getIDCount() > 0 && ptp.getIDAtIndex(0).getSchemeID() != null ? ptp.getIDAtIndex(0).getSchemeID() : null;
            String payeePartyName = ptp.getName() != null ? ptp.getName().getValue() : null;
            String payeeLegalRegistrationId = ptp.getSpecifiedLegalOrganization() != null && ptp.getSpecifiedLegalOrganization().getID() != null ? ptp.getSpecifiedLegalOrganization().getID().getValue() : null;
            String payeeLegalRegistrationSchemeId = ptp.getSpecifiedLegalOrganization() != null && ptp.getSpecifiedLegalOrganization().getID() != null ? ptp.getSpecifiedLegalOrganization().getID().getSchemeID() : null;

            Payee thePayee = new Payee(invoiceId, payeePartyId, payeeSchemeId, payeePartyName, payeeLegalRegistrationId, payeeLegalRegistrationSchemeId);
            payeeRepo.save(thePayee);
            logger.debug(thePayee.toString());
            logger.debug("END PAYEE - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty() != null) {
            logger.debug("SELLER_TAX_REPRESENTATIVE - CII");
            TradePartyType str = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeAgreement().getSellerTaxRepresentativeTradeParty();
            String taxRepPartyName = str.getSpecifiedLegalOrganization() != null && str.getSpecifiedLegalOrganization().getTradingBusinessName() != null ? str.getSpecifiedLegalOrganization().getTradingBusinessName().getValue() : null;
            String taxRepAddress1 = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getLineOne() != null ? str.getPostalTradeAddress().getLineOne().getValue() : null;
            String taxRepAddress2 = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getLineTwo() != null ? str.getPostalTradeAddress().getLineTwo().getValue() : null;
            String taxRepAddress3 = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getLineThree() != null ? str.getPostalTradeAddress().getLineThree().getValue() : null;
            String taxRepCity = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getCityName() != null ? str.getPostalTradeAddress().getCityName().getValue() : null;
            String taxRepPostCode = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getPostcodeCode() != null ? str.getPostalTradeAddress().getPostcodeCode().getValue() : null;
            String taxRepCountrySubdivision = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getCountrySubDivisionNameCount() > 0 ? str.getPostalTradeAddress().getCountrySubDivisionNameAtIndex(0).getValue() : null;
            String taxRepCountryCode = str.getPostalTradeAddress() != null && str.getPostalTradeAddress().getCountryID() != null ? str.getPostalTradeAddress().getCountryID().getValue() : null;
            String taxRepRegistrationId = str.getSpecifiedTaxRegistrationCount() > 0 && str.getSpecifiedTaxRegistrationAtIndex(0).getID() != null ? str.getSpecifiedTaxRegistrationAtIndex(0).getID().getValue() : null;
            String taxRepSchemeId = str.getSpecifiedTaxRegistrationCount() > 0 && str.getSpecifiedTaxRegistrationAtIndex(0).getID() != null && str.getSpecifiedTaxRegistrationAtIndex(0).getID().getSchemeID() != null ? str.getSpecifiedTaxRegistrationAtIndex(0).getID().getSchemeID() : null;

            SellerTaxRepresentative theSellerTaxRepresentative = new SellerTaxRepresentative(invoiceId, taxRepPartyName, taxRepAddress1, taxRepAddress2, taxRepAddress3, taxRepCity,
                    taxRepPostCode, taxRepCountrySubdivision, taxRepCountryCode, taxRepRegistrationId, taxRepSchemeId);
            sellerTaxRepresentativeRepo.save(theSellerTaxRepresentative);
            logger.debug(theSellerTaxRepresentative.toString());
            logger.debug("END SELLER_TAX_REPRESENTATIVE - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementPaymentMeansCount() > 0) {
            logger.debug("START PAYMENT_MEANS - CII");
            List<TradeSettlementPaymentMeansType> paymentMeansList = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementPaymentMeans();
            for (TradeSettlementPaymentMeansType p : paymentMeansList) {
                String paymMeansTypeCode = p.getTypeCode() != null ? p.getTypeCode().getValue() : null;
                String paymMeansTypeCodeName = p.getInformationCount() > 0 ? p.getInformationAtIndex(0).getValue() : null;
                String paymentInfo = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getPaymentReferenceCount() > 0 ? aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getPaymentReferenceAtIndex(0).getValue() : null;
                String cardAccountNumber = p.getApplicableTradeSettlementFinancialCard() != null && p.getApplicableTradeSettlementFinancialCard().getID() != null ?
                        p.getApplicableTradeSettlementFinancialCard().getID().getValue() : null;

                String cardNetworkId = null;
                String cardHolderName = p.getApplicableTradeSettlementFinancialCard() != null && p.getApplicableTradeSettlementFinancialCard().getCardholderName() != null ? p.getApplicableTradeSettlementFinancialCard().getCardholderName().getValue() : null;
                String creditAccountId = p.getPayeePartyCreditorFinancialAccount() != null && p.getPayeePartyCreditorFinancialAccount().getIBANID() != null ? p.getPayeePartyCreditorFinancialAccount().getIBANID().getValue() : null;
                String creditAccountName = p.getPayeePartyCreditorFinancialAccount() != null && p.getPayeePartyCreditorFinancialAccount().getAccountName() != null ? p.getPayeePartyCreditorFinancialAccount().getAccountName().getValue() : null;
                String creditServiceProviderId = p.getPayerSpecifiedDebtorFinancialInstitution() != null && p.getPayerSpecifiedDebtorFinancialInstitution().getBICID() != null ? p.getPayerSpecifiedDebtorFinancialInstitution().getBICID().getValue() : null;
                String mandateReferenceId = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsCount() > 0 &&
                        aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDirectDebitMandateIDCount() > 0 ?
                        aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradePaymentTermsAtIndex(0).getDirectDebitMandateIDAtIndex(0).getValue() : null;
                String debitedAccountId = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getCreditorReferenceID() != null ? aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getCreditorReferenceID().getValue() : null;

                PaymentMeans thePaymentMeans = new PaymentMeans(invoiceId, paymMeansTypeCode, paymMeansTypeCodeName, paymentInfo, cardAccountNumber, cardNetworkId, cardHolderName,
                        creditAccountId, creditAccountName, creditServiceProviderId, mandateReferenceId, debitedAccountId);
                paymentMeansRepo.save(thePaymentMeans);
                logger.debug(thePaymentMeans.toString());
            }
            logger.debug("END PAYMENT_MEANS - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeAllowanceChargeCount() > 0) {
            logger.debug("START ALLOWANCE_CHARGE - CII");
            List<TradeAllowanceChargeType> allowanceChargeList = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeAllowanceCharge();
            for (TradeAllowanceChargeType a : allowanceChargeList) {
                boolean allChargeChargeIndicator = a.getChargeIndicator() != null;
                String allChargeReasonCode = a.getReasonCode() != null ? a.getReasonCode().getValue() : null;
                String allChargeReason = a.getReason() != null ? a.getReason().getValue() : null;
                BigDecimal percentage = a.getCalculationPercent() != null ? a.getCalculationPercent().getValue() : null;
                BigDecimal amount = a.getActualAmountCount() > 0 ? a.getActualAmountAtIndex(0).getValue() : null;
                String amountCurrencyId = a.getActualAmountCount() > 0 ? a.getActualAmountAtIndex(0).getCurrencyID() : null;
                BigDecimal baseAmount = a.getBasisAmount() != null ? a.getBasisAmount().getValue() : null;
                String baseAmountCurrencyId = a.getBasisAmount() != null ? a.getBasisAmount().getCurrencyID() : null;
                String vatCategoryCode = a.getCategoryTradeTaxCount() > 0 && a.getCategoryTradeTaxAtIndex(0).getTypeCode() != null ? a.getCategoryTradeTaxAtIndex(0).getTypeCode().getValue() : null;
                BigDecimal vatRate = a.getCategoryTradeTaxCount() > 0 && a.getCategoryTradeTaxAtIndex(0).getRateApplicablePercent() != null ? a.getCategoryTradeTaxAtIndex(0).getRateApplicablePercent().getValue() : null;
                String vatSchemeId = null;

                AllowanceCharge theAllowanceCharge = new AllowanceCharge(invoiceId, allChargeChargeIndicator, allChargeReasonCode, allChargeReason, percentage, amount, amountCurrencyId, baseAmount, baseAmountCurrencyId,
                        vatCategoryCode, vatRate, vatSchemeId);
                allowanceChargeRepo.save(theAllowanceCharge);
                logger.debug(theAllowanceCharge.toString());
            }
            logger.debug("END ALLOWANCE_CHARGE - CII");
        }

        logger.debug("START TAX_TOTAL - CII");
        BigDecimal vatAmount = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation().getTaxTotalAmountCount() > 0 ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation().getTaxTotalAmountAtIndex(0).getValue() : null;

        String vatAmountCurrencyId = aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation().getTaxTotalAmountCount() > 0 ?
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation().getTaxTotalAmountAtIndex(0).getCurrencyID() : null;
        TaxTotal theTaxTotal = new TaxTotal(invoiceId, vatAmount, vatAmountCurrencyId);
        taxTotalRepo.save(theTaxTotal);
        logger.debug(theTaxTotal.toString());
        Long totalId = theTaxTotal.getRowId();
        logger.debug("END TAX_TOTAL - CII");

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTaxCount() > 0) {
            List<TradeTaxType> taxTotalList = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getApplicableTradeTax();
            logger.debug("START TAX_SUBTOTAL - CII");
            for (TradeTaxType t : taxTotalList) {
                BigDecimal taxableAmount = t.getBasisAmountCount() > 0 ? t.getBasisAmountAtIndex(0).getValue() : null;
                String taxableAmountCurrencyId = t.getBasisAmountCount() > 0 ? t.getBasisAmountAtIndex(0).getCurrencyID() : null;
                BigDecimal taxAmount = t.getCalculatedAmountCount() > 0 ? t.getCalculatedAmountAtIndex(0).getValue() : null;
                String taxAmountCurrencyId = t.getCalculatedAmountCount() > 0 ? t.getCalculatedAmountAtIndex(0).getCurrencyID() : null;
                String categoryCode = t.getTypeCode() != null ? t.getTypeCode().getValue() : null;
                BigDecimal categoryRate = t.getRateApplicablePercent() != null ? t.getRateApplicablePercent().getValue() : null;
                String exemptionReasonCode = t.getExemptionReasonCode() != null ? t.getExemptionReasonCode().getValue() : null;
                String exemptionReasonText = t.getExemptionReason() != null ? t.getExemptionReason().getValue() : null;
                String taxSchemeId = null;

                TaxSubtotal theTaxSubtotal = new TaxSubtotal(totalId, taxableAmount, taxableAmountCurrencyId, taxAmount, taxAmountCurrencyId, categoryCode, categoryRate,
                        exemptionReasonCode, exemptionReasonText, taxSchemeId);
                taxSubtotalRepo.save(theTaxSubtotal);
                logger.debug(theTaxSubtotal.toString());
            }
            logger.debug("END TAX_SUBTOTAL - CII");
        }

        if (aCIIObject.getSupplyChainTradeTransaction() != null && aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement() != null &&
                aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation() != null) {
            TradeSettlementHeaderMonetarySummationType dt = aCIIObject.getSupplyChainTradeTransaction().getApplicableHeaderTradeSettlement().getSpecifiedTradeSettlementHeaderMonetarySummation();
            logger.debug("START DOCUMENT_TOTALS - CII");
            BigDecimal netAmount = dt.getInformationAmountCount() > 0 ? dt.getLineTotalAmountAtIndex(0).getValue() : null;
            String netAmountCurrencyId = dt.getInformationAmountCount() > 0 ? dt.getLineTotalAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal amountWithoutVat = dt.getTaxBasisTotalAmountCount() > 0 ? dt.getTaxBasisTotalAmountAtIndex(0).getValue() : null;
            String amountWithoutVatCurrencyId = dt.getTaxBasisTotalAmountCount() > 0 ? dt.getTaxBasisTotalAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal amountWithVat = dt.getGrandTotalAmountCount() > 0 ? dt.getGrandTotalAmountAtIndex(0).getValue() : null;
            String amountWithVatCurrencyId = dt.getGrandTotalAmountCount() > 0 ? dt.getGrandTotalAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal allowancesTotal = dt.getAllowanceTotalAmountCount() > 0 ? dt.getAllowanceTotalAmountAtIndex(0).getValue() : null;
            String allowancesTotalCurrencyId = dt.getAllowanceTotalAmountCount() > 0 ? dt.getAllowanceTotalAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal chargesTotal = dt.getChargeTotalAmountCount() > 0 ? dt.getChargeTotalAmountAtIndex(0).getValue() : null;
            String chargesTotalCurrencyId = dt.getChargeTotalAmountCount() > 0 ? dt.getChargeTotalAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal prepaidAmount = dt.getTotalPrepaidAmountCount() > 0 ? dt.getTotalPrepaidAmountAtIndex(0).getValue() : null;
            String prepaidAmountCurrencyId = dt.getTotalPrepaidAmountCount() > 0 ? dt.getTotalPrepaidAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal roundingAmount = dt.getRoundingAmountCount() > 0 ? dt.getRoundingAmountAtIndex(0).getValue() : null;
            String roundingAmountCurrencyId = dt.getRoundingAmountCount() > 0 ? dt.getRoundingAmountAtIndex(0).getCurrencyID() : null;
            BigDecimal payableAmount = dt.getDuePayableAmountCount() > 0 ? dt.getDuePayableAmountAtIndex(0).getValue() : null;
            String payableAmountCurrencyId = dt.getDuePayableAmountCount() > 0 ? dt.getDuePayableAmountAtIndex(0).getCurrencyID() : null;

            DocumentTotals theDocumentTotals = new DocumentTotals(invoiceId, netAmount, netAmountCurrencyId, amountWithoutVat, amountWithoutVatCurrencyId, amountWithVat,
                    amountWithVatCurrencyId, allowancesTotal, allowancesTotalCurrencyId, chargesTotal, chargesTotalCurrencyId, prepaidAmount, prepaidAmountCurrencyId, roundingAmount,
                    roundingAmountCurrencyId, payableAmount, payableAmountCurrencyId);
            documentTotalsRepo.save(theDocumentTotals);
            logger.debug(theDocumentTotals.toString());
            logger.debug("END DOCUMENT_TOTALS - CII");
        }

        logger.debug("START INVOICE_LINE - CII");
        List<SupplyChainTradeLineItemType> invoiceLineList = aCIIObject.getSupplyChainTradeTransaction().getIncludedSupplyChainTradeLineItem();
        for (SupplyChainTradeLineItemType i : invoiceLineList) {
            String invoiceLineId = i.getAssociatedDocumentLineDocument() != null && i.getAssociatedDocumentLineDocument().getLineID() != null ?
                    i.getAssociatedDocumentLineDocument().getLineID().getValue() : null;
            String invoiceLineNote = i.getAssociatedDocumentLineDocument() != null && i.getAssociatedDocumentLineDocument().getIncludedNoteCount() > 0 &&
                    i.getAssociatedDocumentLineDocument().getIncludedNoteAtIndex(0).getContentCount() > 0 ?
                    i.getAssociatedDocumentLineDocument().getIncludedNoteAtIndex(0).getContentAtIndex(0).getValue() : null;
            BigDecimal invoiceLineQuantity = i.getSpecifiedLineTradeDelivery() != null && i.getSpecifiedLineTradeDelivery().getBilledQuantity() != null ?
                    i.getSpecifiedLineTradeDelivery().getBilledQuantity().getValue() : null;
            String unitCode = i.getSpecifiedLineTradeDelivery() != null && i.getSpecifiedLineTradeDelivery().getBilledQuantity() != null ?
                    i.getSpecifiedLineTradeDelivery().getBilledQuantity().getUnitCode() : null;
            BigDecimal lineExtensionNetAmount = i.getSpecifiedLineTradeSettlement() != null &&
                    i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation() != null &&
                    i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation().getLineTotalAmountCount() > 0 ?
                    i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation().getLineTotalAmountAtIndex(0).getValue() : null;
            String lineExtensionNetAmountCurrencyId = i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation() != null &&
                    i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation().getLineTotalAmountCount() > 0 ?
                    i.getSpecifiedLineTradeSettlement().getSpecifiedTradeSettlementLineMonetarySummation().getLineTotalAmountAtIndex(0).getCurrencyID() : null;
            String accountingReferenceId = i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccountCount() > 0 &&
                    i.getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccountAtIndex(0).getID() != null ?
                    i.getSpecifiedLineTradeSettlement().getReceivableSpecifiedTradeAccountingAccountAtIndex(0).getID().getValue() : null;
            String lineReferenceId = i.getSpecifiedLineTradeAgreement() != null && i.getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument() != null &&
                    i.getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument().getLineID() != null ?
                    i.getSpecifiedLineTradeAgreement().getBuyerOrderReferencedDocument().getLineID().getValue() : null;
            String objectId = i.getSpecifiedLineTradeAgreement() != null && i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentCount() > 0 &&
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID() != null ?
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID().getValue() : null;
            String objectSchemeId = i.getSpecifiedLineTradeAgreement() != null && i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentCount() > 0 &&
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID() != null ?
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getIssuerAssignedID().getSchemeID() : null;
            String typeCode = i.getSpecifiedLineTradeAgreement() != null && i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentCount() > 0 &&
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getTypeCode() != null ?
                    i.getSpecifiedLineTradeAgreement().getAdditionalReferencedDocumentAtIndex(0).getTypeCode().getValue() : null;

            InvoiceLine theInvoiceLine = new InvoiceLine(invoiceId, invoiceLineId, invoiceLineNote, invoiceLineQuantity, unitCode, lineExtensionNetAmount, lineExtensionNetAmountCurrencyId,
                    accountingReferenceId, lineReferenceId, objectId, objectSchemeId, typeCode);
            invoiceLineRepo.save(theInvoiceLine);
            logger.debug(theInvoiceLine.toString());
            Long lineId = theInvoiceLine.getRowId();

            if (i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod() != null) {
                logger.debug("START INVOICE_PERIOD_COUNT - CII");
                Date invPeriodStartDate = i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getStartDateTime() != null && i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getStartDateTime().getDateTime() != null ? i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getStartDateTime().getDateTime().toGregorianCalendar().getTime() : null;
                Date invPeriodEndDate = i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getEndDateTime() != null && i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getEndDateTime().getDateTime() != null ? i.getSpecifiedLineTradeSettlement().getBillingSpecifiedPeriod().getEndDateTime().getDateTime().toGregorianCalendar().getTime() : null;
                String taxPointDateCode = null;
                InvoiceLinePeriod theInvoiceLinePeriod = new InvoiceLinePeriod(lineId, invPeriodStartDate, invPeriodEndDate, taxPointDateCode);
                invoiceLinePeriodRepo.save(theInvoiceLinePeriod);
                logger.debug(theInvoiceLinePeriod.toString());
                logger.debug("END INVOICE_PERIOD_COUNT - CII");
            }

            if (i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceChargeCount() > 0) {
                logger.debug("START INVOICE_LINE_ALLOWANCE_CHARGE - CII");
                List<TradeAllowanceChargeType> invoiceLineAllowanceChargeList = i.getSpecifiedLineTradeSettlement().getSpecifiedTradeAllowanceCharge();
                for (TradeAllowanceChargeType a : invoiceLineAllowanceChargeList) {
                    boolean invLineAllChargeIndicator = i.getSpecifiedLineTradeAgreement().getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getChargeIndicator() != null;
                    String invLineReasonCode = a.getReasonCode() != null ? a.getReasonCode().getValue() : null;
                    String invLineReason = a.getReason() != null ? a.getReason().getValue() : null;
                    BigDecimal invLinePercentage = a.getCalculationPercent() != null ? a.getCalculationPercent().getValue() : null;
                    BigDecimal invLineChargeAmount = a.getActualAmountCount() > 0 ? a.getActualAmountAtIndex(0).getValue() : null;
                    String invLineAmountCurrencyId = a.getActualAmountCount() > 0 ? a.getActualAmountAtIndex(0).getCurrencyID() : null;
                    BigDecimal invLineBaseAmount = a.getBasisAmount() != null ? a.getBasisAmount().getValue() : null;
                    String invLineBaseAmountCurrencyId = a.getBasisAmount() != null ? a.getBasisAmount().getCurrencyID() : null;

                    InvoiceLineAllowanceCharge theInvoiceLineAllowanceCharge = new InvoiceLineAllowanceCharge(lineId, invLineAllChargeIndicator, invLineReasonCode, invLineReason, invLinePercentage, invLineChargeAmount,
                            invLineAmountCurrencyId, invLineBaseAmount, invLineBaseAmountCurrencyId);
                    invoiceLineAllowanceChargeRepo.save(theInvoiceLineAllowanceCharge);
                    logger.debug(theInvoiceLineAllowanceCharge.toString());
                }
                logger.debug("END INVOICE_LINE_ALLOWANCE_CHARGE - CII");
            }

            if (i.getSpecifiedLineTradeAgreement() != null) {
                LineTradeAgreementType ip = i.getSpecifiedLineTradeAgreement();
                logger.debug("START INVOICE_LINE_PRICE - CII");
                BigDecimal priceNetAmount = ip.getNetPriceProductTradePrice() != null && ip.getNetPriceProductTradePrice().getChargeAmountCount() > 0 ?
                        ip.getNetPriceProductTradePrice().getChargeAmountAtIndex(0).getValue() : null;
                String priceNetAmountCurrencyId = ip.getNetPriceProductTradePrice() != null && ip.getNetPriceProductTradePrice().getChargeAmountCount() > 0 ?
                        ip.getNetPriceProductTradePrice().getChargeAmountAtIndex(0).getCurrencyID() : null;
                BigDecimal priceBaseQuantity = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getBasisQuantity() != null ?
                        ip.getGrossPriceProductTradePrice().getBasisQuantity().getValue() : null;
                String priceBaseQuantityUnitCode = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getBasisQuantity() != null ? ip.getGrossPriceProductTradePrice().getBasisQuantity().getUnitCode() : null;
                Boolean priceChargeIndicator = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeCount() > 0 ? ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getChargeIndicator() != null : null;
                BigDecimal priceDiscount = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeCount() > 0 && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getActualAmountCount() > 0 ? ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getActualAmountAtIndex(0).getValue() : null;
                String priceDiscountCurrencyId = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeCount() > 0 && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getActualAmountCount() > 0 ? ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getActualAmountAtIndex(0).getCurrencyID() : null;
                BigDecimal grossPrice = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeCount() > 0 && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getBasisQuantity() != null ? ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getBasisQuantity().getValue() : null;
                String grossPriceCurrencyId = ip.getGrossPriceProductTradePrice() != null && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeCount() > 0 && ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getBasisQuantity() != null ? ip.getGrossPriceProductTradePrice().getAppliedTradeAllowanceChargeAtIndex(0).getBasisQuantity().getUnitCode() : null;

                InvoiceLinePrice theInvoiceLinePrice = new InvoiceLinePrice(lineId, priceNetAmount, priceNetAmountCurrencyId, priceBaseQuantity, priceBaseQuantityUnitCode, priceChargeIndicator,
                        priceDiscount, priceDiscountCurrencyId, grossPrice, grossPriceCurrencyId);
                invoiceLinePriceRepo.save(theInvoiceLinePrice);
                logger.debug(theInvoiceLinePrice.toString());
                logger.debug("END INVOICE_LINE_PRICE - CII");
            }

            if (i.getSpecifiedTradeProduct() != null) {
                TradeProductType is = i.getSpecifiedTradeProduct();
                logger.debug("START INVOICE_LINE_ITEM - CII");
                String itemDescription = is.getDescription() != null ? is.getDescription().getValue() : null;
                String itemName = is.getNameCount() > 0 ? is.getNameAtIndex(0).getValue() : null;
                String itemBuyersId = is.getBuyerAssignedID() != null ? is.getBuyerAssignedID().getValue() : null;
                String itemSellersId = is.getSellerAssignedID() != null ? is.getSellerAssignedID().getValue() : null;
                String itemStandardId = is.getGlobalID() != null ? is.getGlobalID().getValue() : null;
                String itemStandardSchemeId = is.getGlobalID() != null ? is.getGlobalID().getSchemeID() : null;
                String itemOriginCountry = is.getOriginTradeCountry() != null && is.getOriginTradeCountry().getNameCount() > 0 ? is.getOriginTradeCountry().getNameAtIndex(0).getValue() : null;

                String itemVatCategoryCode = i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxCount() > 0 &&
                        i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxAtIndex(0).getCategoryCode() != null ?
                        i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxAtIndex(0).getCategoryCode().getValue() : null;
                BigDecimal itemVatRate = i.getSpecifiedLineTradeSettlement() != null && i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxCount() > 0 &&
                        i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxAtIndex(0).getRateApplicablePercent() != null ?
                        i.getSpecifiedLineTradeSettlement().getApplicableTradeTaxAtIndex(0).getRateApplicablePercent().getValue() : null;

                InvoiceLineItem theInvoiceLineItem = new InvoiceLineItem(lineId, itemDescription, itemName, itemBuyersId, itemSellersId, itemStandardId, itemStandardSchemeId, itemOriginCountry,
                        itemVatCategoryCode, itemVatRate, null);

                invoiceLineItemRepo.save(theInvoiceLineItem);
                logger.debug(theInvoiceLineItem.toString());
                Long itemId = theInvoiceLineItem.getRowId();

                if (i.getSpecifiedTradeProduct().getDesignatedProductClassificationCount() > 0) {
                    logger.debug("START ITEM_CLASSIFICATION - CII");
                    List<ProductClassificationType> itemClassList = i.getSpecifiedTradeProduct().getDesignatedProductClassification();
                    for (ProductClassificationType c : itemClassList) {
                        String itemClassId = c.getClassCode() != null ? c.getClassCode().getValue() : null;
                        String itemClassSchemeId = c.getClassCode() != null ? c.getClassCode().getListID() : null;
                        String itemClassSchemeVersionId = c.getClassCode() != null ? c.getClassCode().getListVersionID() : null;
                        ItemClassification theItemClassification = new ItemClassification(itemId, itemClassId, itemClassSchemeId, itemClassSchemeVersionId);
                        itemClassificationRepo.save(theItemClassification);
                        logger.debug(theItemClassification.toString());
                    }
                    logger.debug("END ITEM_CLASSIFICATION - CII");
                }

                if (i.getSpecifiedTradeProduct().getApplicableProductCharacteristicCount() > 0) {
                    logger.debug("START ITEM_PROPERTY - CII");
                    List<ProductCharacteristicType> itemPropertyList = i.getSpecifiedTradeProduct().getApplicableProductCharacteristic();
                    for (ProductCharacteristicType ip : itemPropertyList) {
                        String propName = ip.getDescriptionCount() > 0 ? ip.getDescriptionAtIndex(0).getValue() : null;
                        String propValue = ip.getValueCount() > 0 ? ip.getValueAtIndex(0).getValue() : null;
                        ItemProperty theItemProperty = new ItemProperty(itemId, propName, propValue);
                        itemPropertyRepo.save(theItemProperty);
                        logger.debug(theItemProperty.toString());
                    }
                    logger.debug("END ITEM_PROPERTY - CII");
                }

                logger.debug("END INVOICE_LINE_ITEM - CII");
            }
        }
        logger.debug("END INVOICE_LINE - CII");
    }

    private Date toDate(javax.xml.datatype.XMLGregorianCalendar value) {
        return value != null ? value.toGregorianCalendar().getTime() : null;
    }
}