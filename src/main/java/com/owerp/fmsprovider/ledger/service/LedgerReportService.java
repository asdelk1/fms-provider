package com.owerp.fmsprovider.ledger.service;

import com.owerp.fmsprovider.account.model.data.BookEntryDetails;
import com.owerp.fmsprovider.account.repository.BookEntryDetailsRepository;
import com.owerp.fmsprovider.customer.data.enums.EntryType;
import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.ledger.model.dto.GeneralLedger;
import com.owerp.fmsprovider.ledger.model.dto.ReportData;
import com.owerp.fmsprovider.ledger.model.dto.TrailBalance;
import com.owerp.fmsprovider.ledger.repository.LedgerAccountRepository;
import com.owerp.fmsprovider.system.advice.ApplicationException;
import com.owerp.fmsprovider.system.util.ApplicationProperties;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LedgerReportService {

    private final LedgerAccountService ledgerAccountService;
    private final BookEntryDetailsRepository bookEntryDetailsRepository;
    private final LedgerAccountRepository ledgerAccountRepository;

    public LedgerReportService(LedgerAccountService ledgerAccountService, BookEntryDetailsRepository bookEntryDetailsRepository, LedgerAccountRepository ledgerAccountRepository) {
        this.ledgerAccountService = ledgerAccountService;
        this.bookEntryDetailsRepository = bookEntryDetailsRepository;
        this.ledgerAccountRepository = ledgerAccountRepository;
    }

    public File genTrailBalanceReport(ReportData reportData) {
        return genTrailBalanceReportPDF(reportData);
    }

    public File genGeneralLedgerReport(ReportData reportData) {
        return genGeneralLedgerReportPDF(reportData);
    }

    private File genTrailBalanceReportPDF(ReportData reportData) {
        try {

            String sourcePath = "classpath:templates/reports/ledger/TrailBalanceReport.jrxml";
            File file = ResourceUtils.getFile(sourcePath);

            String distPath = "classpath:templates/pages/reports/ledger";
            File pdfFilePath = ResourceUtils.getFile(distPath);

            final InputStream stream = new FileInputStream(file);

            Object[] res = generateTbList(reportData);

            final JasperReport report = JasperCompileManager.compileReport(stream);
            //final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(new ArrayList<>());
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource((List<GeneralLedger>)res[0]);

            final JasperPrint print = JasperFillManager.fillReport(report, (Map<String, Object>)res[1], source);
            final String filePath = pdfFilePath.getPath() + "/TrailBalance.pdf";
            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);

            return new File(filePath);
        } catch (Exception e) {
           throw new ApplicationException(e.getMessage());
        }
    }

    private Object[] generateTbList(ReportData reportData)throws Exception{
        Object[] res = new Object[2];
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime endDateTime = LocalDate.parse(reportData.getFromDate(), formatter).atTime(23, 59);
            List<TrailBalance> list=new ArrayList<TrailBalance>();

            List<LedgerAccount> accList=ledgerAccountRepository.getLedgerAccountsByStatusOrderByLedgerAccCode(true);
            for(LedgerAccount acc :accList){
                TrailBalance tb=new TrailBalance();
                tb.setSubACName(acc.getLedgerAccName());
                tb.setSubAcNumber(acc.getLedgerAccCode());
                double closingBal=getGLOpeningBalance(endDateTime, acc.getId(), acc.getLedgerCategory().getLedgerType().getClarifi());

                if(acc.getLedgerCategory().getLedgerType().getClarifi().equals("D")){
                    if(closingBal >=0){
                        tb.setDebitAmount(closingBal);
                        tb.setCreditAmount(0.00);
                    }
                    else{
                        tb.setDebitAmount(0.00);
                        tb.setCreditAmount((closingBal * -1));
                    }
                }
                else if(acc.getLedgerCategory().getLedgerType().getClarifi().equals("C")){
                    if(closingBal >=0){
                        tb.setDebitAmount(0.00);
                        tb.setCreditAmount(closingBal);
                    }
                    else{
                        tb.setDebitAmount((closingBal * -1));
                        tb.setCreditAmount(0.00);
                    }
                }
                tb.setAccId(acc.getId());
                list.add(tb);
            }
            res[0] = list;
            final Map<String, Object> parameters = new HashMap<>();
            parameters.put("companyName", ApplicationProperties.COMPANY_NAME);
            parameters.put("endDate", endDateTime);
            res[1] = parameters;

        }catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return res;

    }

    private File genGeneralLedgerReportPDF(ReportData reportData) {

        try {
            String sourcePath = "classpath:templates/reports/ledger/GeneralLedgerReport.jrxml";
            File file = ResourceUtils.getFile(sourcePath);

            String distPath = "classpath:templates/reports/ledger";
            File pdfFilePath = ResourceUtils.getFile(distPath);

            final InputStream stream = new FileInputStream(file);


            Object[] res = generateGL(reportData);

            final JasperReport report = JasperCompileManager.compileReport(stream);
            //final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(new ArrayList<>());
            final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource((List<GeneralLedger>) res[1]);

            final JasperPrint print = JasperFillManager.fillReport(report, (Map<String, Object>) res[2], source);
            final String filePath = pdfFilePath.getPath() + "/GeneralLedger.pdf";
            // Export the report to a PDF file.
            JasperExportManager.exportReportToPdfFile(print, filePath);

            return new File(filePath);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private Object[] generateGL(ReportData reportData) {
        Object[] res = new Object[3];
        // get Dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime startDateTime = LocalDate.parse(reportData.getFromDate(), formatter).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(reportData.getToDate(), formatter).atTime(23, 59);
        LocalDateTime previousDayWithTime = startDateTime.minusDays(1);
        LedgerAccount ledgerAccount = this.ledgerAccountService.get(reportData.getLedgerAccountId()).get();

        res[0] = getGLOpeningBalance(previousDayWithTime, ledgerAccount.getId(), ledgerAccount.getLedgerCategory().getLedgerType().getClarifi());
        res[1] = generateGlLIst(ledgerAccount.getId(), startDateTime, endDateTime, ledgerAccount.getLedgerCategory().getLedgerType().getClarifi(), (Double) res[0]);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("companyName", ApplicationProperties.COMPANY_NAME);
        parameters.put("startDate", startDateTime);
        parameters.put("endDate", endDateTime);
        parameters.put("openingBalance", res[0]);
        parameters.put("accountName", ledgerAccount.getLedgerAccCode() + " - " + ledgerAccount.getLedgerAccName());
        res[2] = parameters;
        return res;

    }

    private List<GeneralLedger> generateGlLIst(Long ledgerAccId, LocalDateTime startDateTime, LocalDateTime endDateTime, String clarifi, Double openingBalance) {
        List<GeneralLedger> list = new ArrayList<GeneralLedger>();
        try {
            double closingBal = openingBalance;

            List<BookEntryDetails> entList = bookEntryDetailsRepository.getEntryListForGl(ledgerAccId, startDateTime, endDateTime);
            for (BookEntryDetails ent : entList) {
                GeneralLedger gl = new GeneralLedger();
                gl.setTransType(ent.getBookEntry().getBookEntryType().getText());
                gl.setEntryNumber(ent.getBookEntry().getEntryNumber());
                gl.setEntryDate(ent.getBookEntry().getEntryDate());
                List<BookEntryDetails> oppAcList = null; //For opposite Accounts
                if (ent.getEntryType() == EntryType.DEBIT) {
                    gl.setCreditAmount(0);
                    gl.setDebitAmount(ent.getAmount());
                    oppAcList = bookEntryDetailsRepository.getOppAccounts(ent.getBookEntry().getId(), EntryType.CREDIT);
                } else if (ent.getEntryType() == EntryType.CREDIT) {
                    gl.setCreditAmount(ent.getAmount());
                    gl.setDebitAmount(0);
                    oppAcList = bookEntryDetailsRepository.getOppAccounts(ent.getBookEntry().getId(), EntryType.DEBIT);
                }
                if (clarifi.equals("D")) {
                    closingBal += ((gl.getDebitAmount() - gl.getCreditAmount()));
                } else if (clarifi.equals("C")) {
                    closingBal += ((gl.getCreditAmount() - gl.getDebitAmount()));
                }
                gl.setClosingBalance(closingBal);
                String opp = "";
                for (BookEntryDetails opAc : oppAcList) {
                    opp += opAc.getLedgerAccount().getLedgerAccCode() + ":" + opAc.getLedgerAccount().getLedgerAccName() + " - " + opAc.getAmount() + "\n";
                }
                gl.setOppAccounts(opp);

                gl.setDetails(ent.getBookEntry().getNote());
                //
                list.add(gl);
            }
            if (entList.size() == 0) {
                GeneralLedger gl = new GeneralLedger();
                gl.setTransType("No records");
                list.add(gl);
            }
        } catch (Exception e) {
            System.out.print("generateGlLIst " + e);
        }
        return list;
    }

    private double getGLOpeningBalance(LocalDateTime startDate, Long ledgerAccId, String clarifi) {
        double openingBalance = 0;
        try {
            double debitSum = 0;
            try {
                debitSum = bookEntryDetailsRepository.getDebitOrCreditSum(ledgerAccId, startDate, EntryType.DEBIT);
            } catch (Exception e) {
                debitSum = 0;
            }

            double creditSum = 0;
            try {
                creditSum = bookEntryDetailsRepository.getDebitOrCreditSum(ledgerAccId, startDate, EntryType.CREDIT);
                ;
            } catch (Exception e) {
                creditSum = 0;
            }

            if (clarifi.equals("D")) {
                openingBalance = (debitSum - creditSum);
            } else if (clarifi.equals("C")) {
                openingBalance = (creditSum - debitSum);
            }
        } catch (Exception e) {
            openingBalance = 0;
        }
        return openingBalance;
    }
}
