package com.sogeti.rental.ui.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import com.sogeti.rental.ui.views.RentalUIConstants;

public class ColorPreferencePage extends FieldEditorPreferencePage implements RentalUIConstants {

	public ColorPreferencePage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		addField(new ColorFieldEditor(PREF_CUSTOMERS_COLOR, "Customer Color", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_RENTAL_COLOR, "Rental Color", getFieldEditorParent()));
		addField(new ColorFieldEditor(PREF_OBJECTS_COLOR, "Objects Color", getFieldEditorParent()));
	}

}
