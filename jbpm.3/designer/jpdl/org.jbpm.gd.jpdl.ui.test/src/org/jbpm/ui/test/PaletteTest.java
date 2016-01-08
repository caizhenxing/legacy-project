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
package org.jbpm.ui.test;



public class PaletteTest extends DesignerEditorTestCase {
	
	public void testForPaletteEntries() {
		assertNotNull("Selection tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Selection"));
		assertNotNull("Marquee tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Marquee"));
		assertNotNull("Start state creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Start"));
		assertNotNull("End state creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.End"));
		assertNotNull("Fork node creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Fork"));
		assertNotNull("Join node creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Join"));
		assertNotNull("Desicion node creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Decision"));
		assertNotNull("Transition creation tool should be present", getPaletteEntryMap().get("org.jbpm.ui.palette.Transition"));
	}

}
