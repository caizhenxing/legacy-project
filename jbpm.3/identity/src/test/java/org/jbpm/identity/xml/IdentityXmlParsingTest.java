/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jbpm.identity.xml;

import java.util.Set;

import junit.framework.TestCase;

import org.jbpm.identity.Group;
import org.jbpm.identity.User;

public class IdentityXmlParsingTest extends TestCase {
  
  IdentityXmlParser identityXmlParser;
  
  public void setUp() {
    identityXmlParser = new IdentityXmlParser();
    identityXmlParser.parse("org/jbpm/identity/xml/identity.xml");
  }

  public void testUser() {
    User ernie = (User) identityXmlParser.users.get("ernie");
    assertEquals("ernie", ernie.getName());
    assertEquals("ernie@sesamestreet.tv", ernie.getEmail());
    assertEquals("canthereyoubert,theresabananainmyear", ernie.getPassword());
  }

  public void testGroup() {
    Group bananalovers = (Group) identityXmlParser.groups.get("bananalovers");
    assertEquals("bananalovers", bananalovers.getName());
    assertEquals("fruitpreference", bananalovers.getType());
  }

  public void testGroupParent() {
    Group sesameinhabitants = (Group) identityXmlParser.groups.get("sesameinhabitants");
    Group bananalovers = (Group) identityXmlParser.groups.get("bananalovers");
    assertSame(sesameinhabitants, bananalovers.getParent());
    assertEquals(1, sesameinhabitants.getChildren().size());
    assertSame(bananalovers, sesameinhabitants.getChildren().iterator().next());
  }

  public void testUserMembership() {
    User ernie = (User) identityXmlParser.users.get("ernie");
    Group bananalovers = (Group) identityXmlParser.groups.get("bananalovers");
    Set erniesMemberships = ernie.getMemberships();
    assertEquals(1, erniesMemberships.size());
    Set bananaloversMemberships = bananalovers.getMemberships();
    assertEquals(1, bananaloversMemberships.size());
    assertSame(bananaloversMemberships.iterator().next(), erniesMemberships.iterator().next());
  }
}
