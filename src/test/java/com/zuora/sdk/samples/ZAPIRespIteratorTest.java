package com.zuora.sdk.samples;

import org.junit.Test;

import com.zuora.sdk.lib.ZAPIResp;
import com.zuora.sdk.lib.ZClient;

public class ZAPIRespIteratorTest {

   @Test
   public void test_iterate_result(){
      // Create a z_client
      ZClient zClient = new ZClient();

      // Create a Catalog resource object with a zclient
      CatalogManager catalogManager = new CatalogManager(zClient);

      // Connect to the End Point using default tenant's credentials
      // and practice APIs
      ZAPIResp resp = null;
      if (new ConnectionManager().isConnected(zClient)) {
        resp = catalogManager.getProducts();
      }

      // follow nextPage if present
      while (resp != null) {
        // iterate on the response programmatically
         ZAPIRespIterator.iterateZAPIResp(resp);
        String nextPageLink = (String)resp.get("nextPage");
        if (nextPageLink == null) {
          resp = null;
        } else {
          resp = catalogManager.getProducts(nextPageLink);
        }
      }
   }
}
