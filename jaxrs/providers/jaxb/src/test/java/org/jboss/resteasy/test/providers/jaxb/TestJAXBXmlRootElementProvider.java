/*
 * JBoss, the OpenSource J2EE webOS Distributable under LGPL license. See terms of license at gnu.org.
 */
package org.jboss.resteasy.test.providers.jaxb;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.test.BaseResourceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * A TestJAXBXmlRootElementProvider.
 *
 * @author <a href="ryan@damnhandy.com">Ryan J. McDonough</a>
 * @version $Revision:$
 */
public class TestJAXBXmlRootElementProvider extends BaseResourceTest
{

   private static final String HTTP_LOCALHOST_8081_JAXB = "http://localhost:8081/jaxb";

   private static final String FAST_PARENT = "Fast Parent";
   private static final String JSON_PARENT = "JSON Parent";

   private static final String XML_PARENT = "XML Parent";


   private static final Logger logger = LoggerFactory
           .getLogger(TestJAXBXmlRootElementProvider.class);

   private JAXBXmlRootElementClient client;
   private JAXBElementClient elementClient;

   private JsonJAXBXmlRootElementClient jsonClient;
   private JsonJAXBElementClient jsonElementClient;

   private FastinfoSetJAXBXmlRootElementClient fastClient;
   private FastinfoSetJAXBElementClient fastElementClient;
   private JunkXmlOrderClient junkClient;

   @Before
   public void setUp() throws Exception
   {
      addPerRequestResource(JAXBXmlRootElementResource.class);
      ResteasyProviderFactory.initializeInstance();
      RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
      client = ProxyFactory.create(JAXBXmlRootElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      elementClient = ProxyFactory.create(JAXBElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      jsonClient = ProxyFactory.create(JsonJAXBXmlRootElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      jsonElementClient = ProxyFactory.create(JsonJAXBElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      fastClient = ProxyFactory.create(FastinfoSetJAXBXmlRootElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      fastElementClient = ProxyFactory.create(FastinfoSetJAXBElementClient.class, HTTP_LOCALHOST_8081_JAXB);
      junkClient = ProxyFactory.create(JunkXmlOrderClient.class, HTTP_LOCALHOST_8081_JAXB);
   }

   /**
    * FIXME Comment this
    */
   @Test
   public void testGetParent()
   {
      Parent parent = client.getParent(XML_PARENT);
      Assert.assertEquals(parent.getName(), XML_PARENT);
   }

   @Test
   public void testGetParentJunk()
   {
      Parent parent = junkClient.getParent(XML_PARENT);
      Assert.assertEquals(parent.getName(), XML_PARENT);
   }

   @Test
   public void testGetParentElement()
   {
      JAXBElement<Parent> element = elementClient.getParent(XML_PARENT);
      Parent parent = element.getValue();
      Assert.assertEquals(parent.getName(), XML_PARENT);
   }

   @Test
   public void testGetParentFast()
   {
      Parent parent = fastClient.getParent(FAST_PARENT);
      Assert.assertNotNull(parent);
      Assert.assertEquals(parent.getName(), FAST_PARENT);
   }

   @Test
   public void testGetParentElementFast()
   {
      JAXBElement<Parent> element = fastElementClient.getParent(XML_PARENT);
      Parent parent = element.getValue();
      Assert.assertEquals(parent.getName(), XML_PARENT);
   }

   @Test
   public void testGetParentJson() throws Exception
   {
      Parent parent = jsonClient.getParent(JSON_PARENT);
      Assert.assertNotNull(parent);
      Assert.assertEquals(parent.getName(), JSON_PARENT);

      String mapped = jsonClient.getParentString(JSON_PARENT);
      System.out.println("Mapped: '" + mapped + "'");
      String badger = jsonClient.getParentBadger(JSON_PARENT);
      System.out.println("Badger: '" + badger + "'");
      Assert.assertTrue(!badger.equals(mapped));
   }

   @Test
   public void testGetParentElementJson()
   {
      JAXBElement<Parent> element = jsonElementClient.getParent(JSON_PARENT);
      Parent parent = element.getValue();
      Assert.assertEquals(parent.getName(), JSON_PARENT);
   }

   /**
    * FIXME Comment this
    */
   @Test
   public void testPostParent()
   {
      client.postParent(Parent.createTestParent("TEST"));
   }

   @Test
   public void testPostParentFast()
   {
      fastClient.postParent(Parent.createTestParent("TEST FAST"));
   }

   @Test
   public void testPostParentElement()
   {
      Parent parent = Parent.createTestParent("TEST ELEMENT");
      JAXBElement<Parent> parentElement = new JAXBElement<Parent>(new QName("parent"),
              Parent.class, parent);
      elementClient.postParent(parentElement);
   }

}