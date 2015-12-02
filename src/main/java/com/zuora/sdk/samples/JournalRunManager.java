package com.zuora.sdk.samples;

import java.net.URLEncoder;

import com.zuora.sdk.lib.ZAPIArgs;
import com.zuora.sdk.lib.ZAPIResp;
import com.zuora.sdk.lib.ZClient;

public class JournalRunManager {
	private ZClient zClient;
	 
    public JournalRunManager(ZClient zClient){
       this.zClient = zClient;
    }
    
	public void createJournalRun(){
		 ZAPIArgs args = new ZAPIArgs();
	     args.set("uri", ResourceEndpoints.POST_JOURNAL_RUN);


	      args.set("reqBody", new ZAPIArgs());
	      args.getArg("reqBody").set("journalEntryDate", "2014-11-30");
	      //Setting only the accounting period will create a Journal Run for the whole Accounting Period
	      args.getArg("reqBody").set("accountingPeriodName", "Nov-2014");
	      //Setting only the start date will create a Journal Run only for a single day
	      args.getArg("reqBody").set("targetStartDate", "2014-11-01");
	      //Setting the start date and end date within the same accounting period will generate a Journal Run of a date range
	      args.getArg("reqBody").set("targetEndDate", "2014-11-30");
	      
	      //If creating a Journal Run not for an Accounting Period for all Transasction Types, use the following
	      //If Invoice Adjustment is enabled in the tenant
	      //String[] transactionTypes = "Invoice Item|Invoice Adjustment|Taxation Item|Invoice Item Adjustment (Invoice)|Invoice Item Adjustment (Tax)|Electronic Payment|External Payment|Electronic Refund|External Refund|Electronic Credit Balance Payment|External Credit Balance Payment|Electronic Credit Balance Refund|External Credit Balance Refund|Credit Balance Adjustment (Applied from Credit Balance)|Credit Balance Adjustment (Transferred to Credit Balance)".split(Pattern.quote("|"));
	      //If Invoice Adjustment is not enabled in the tenant
	      String[] transactionTypes = "Invoice Item|Taxation Item|Invoice Item Adjustment (Invoice)|Invoice Item Adjustment (Tax)|Electronic Payment|External Payment|Electronic Refund|External Refund|Electronic Credit Balance Payment|External Credit Balance Payment|Electronic Credit Balance Refund|External Credit Balance Refund|Credit Balance Adjustment (Applied from Credit Balance)|Credit Balance Adjustment (Transferred to Credit Balance)".split(Pattern.quote("|"));
	      
	      //If creating a Journal Run for an Accounting Period for all Transasction Types, use the following
	      //If Invoice Adjustment is not enabled in the tenant
	      //String[] transactionTypes = "Invoice Item|Taxation Item|Invoice Item Adjustment (Invoice)|Invoice Item Adjustment (Tax)|Electronic Payment|External Payment|Electronic Refund|External Refund|Electronic Credit Balance Payment|External Credit Balance Payment|Electronic Credit Balance Refund|External Credit Balance Refund|Credit Balance Adjustment (Applied from Credit Balance)|Credit Balance Adjustment (Transferred to Credit Balance)|Revenue Event Item".split(Pattern.quote("|"));
	      //If Invoice Adjustment is enabled in the tenant
	      //String[] transactionTypes = new String[]{"All"};
	      
	      
	      args.getArg("reqBody").setArray("transactionTypes");
	      for (int i=0;i<transactionTypes.length;i++){
		      args.getArg("reqBody").set("transactionTypes["+i+"]",new ZAPIArgs());
		      args.getArg("reqBody").getArg("transactionTypes["+i+"]").set("type", transactionTypes[i]);
	      }
	      
  
	      System.out.println("========== CREATE JOURNAL RUN ============");

	      try {
	        ZAPIResp response = zClient.post(args);
	        System.out.println(response.toJSONString());
	      } catch (IllegalArgumentException e) {
	        System.out.println(e.getMessage());
	      } catch (RuntimeException e) {
	        System.out.println(e.getMessage());
	      }
	      
	      
	}
	
	public void getJournalRun(String jrNumber){
		  try {
			  jrNumber = URLEncoder.encode(jrNumber, "UTF-8");
		     } catch (Exception e) {
		       System.out.println(e.getMessage());
		       e.printStackTrace();
		       return;
		     }
		     
		     ZAPIArgs args = new ZAPIArgs();
		     args.set("uri", ResourceEndpoints.GET_JOURNAL_RUN.replace("{jr-number}", jrNumber));

		     System.out.println( "========== GET JOURNAL RUN INFORMATION ============");

		     try {
		       ZAPIResp response = zClient.get(args);
		       System.out.println(response.toJSONString());
		     } catch (IllegalArgumentException e) {
		       System.out.println(e.getMessage());
		     } catch (RuntimeException e) {
		       System.out.println(e.getMessage());
		     }
	}
}
