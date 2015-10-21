package com.sogeti.rental.ui;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MDirectMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MHandledMenuItem;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuContribution;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class AddMenuItemProcessor {

	private static final String NEW_MENUCONTRIBUTION_ID = "com.sogeti.rental.ui.menucontributionfromprocessor";
	
	public AddMenuItemProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Inject	MApplication application;
	@Inject	EModelService modelService;
	
	@Execute
	public void execute() {
		
		// check if menu exists sinon rajouter  -clearPersistedState dans le launchConfiguration
//		for(MMenuContribution menuContribution : application.getMenuContributions()) {
//			if (NEW_MENUCONTRIBUTION_ID.equals(menuContribution.getElementId()))
//				return;
//		}
		
		// 
		MMenuContribution newMMenuContribution = modelService.createModelElement(MMenuContribution.class);
		newMMenuContribution.setElementId(NEW_MENUCONTRIBUTION_ID);
		newMMenuContribution.setParentId("help");
		newMMenuContribution.setPositionInParent("after=additions");
		
		MDirectMenuItem handledMenuItem = modelService.createModelElement(MDirectMenuItem.class);
//		MHandledMenuItem handledMenuItem = modelService.createModelElement(MHandledMenuItem.class);
		handledMenuItem.setLabel("Menu from Processor");
		handledMenuItem.setIconURI("platform:/plugin/com.sogeti.rental.ui/icons/sample.gif");
		handledMenuItem.setContributionURI("bundleclass://com.sogeti.rental.ui/com.sogeti.rental.ui.handlers.MessageHandler");
		newMMenuContribution.getChildren().add(handledMenuItem);
		
		application.getMenuContributions().add(newMMenuContribution);
	}
}
