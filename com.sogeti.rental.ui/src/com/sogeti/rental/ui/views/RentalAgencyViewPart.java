package com.sogeti.rental.ui.views;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;

import com.opcoach.training.rental.RentalAgency;

public class RentalAgencyViewPart implements RentalUIConstants
{
  TreeViewer rentalTreeViewer;
  
//	@Inject @Named(RENTAL_UI_IMGREGISTRY)
//	ImageRegistry localImageRegistry;


  public RentalAgencyViewPart()
  {
  }
  

  @PostConstruct
  public void createPartControl(Composite parent, RentalAgency agency, IEclipseContext context)
  {
    GridLayout gl_parent = new GridLayout(1, false);
    gl_parent.verticalSpacing = 1;
    gl_parent.marginWidth = 0;
    gl_parent.marginHeight = 0;
    parent.setLayout(gl_parent);
    
    Composite toolComposite = new Composite(parent, SWT.NONE);
    RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
    toolComposite.setLayout(rl_composite);
    
    final Button expandButton = new Button(toolComposite, SWT.NONE);
    expandButton.setText("+");
//    expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_EXPAND_ALL));
    
    expandButton.addListener(SWT.Selection, new Listener()
    {
      boolean expand = true;
      
      @Override
      public void handleEvent(Event event)
      {
        if (expand)
        {
          rentalTreeViewer.expandAll();
//          expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_COLLASPSE_ALL));
          expandButton.setText("-");
        }
        else
        {
          rentalTreeViewer.collapseAll();
//          expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_EXPAND_ALL));
          expandButton.setText("+");
        }
        expand = !expand;
      }
    });
    
    
    
    //
    rentalTreeViewer = new TreeViewer(parent);
    Tree tree = rentalTreeViewer.getTree();
    tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    RentalProvider rentalProvider = ContextInjectionFactory.make(RentalProvider.class, context);
    rentalTreeViewer.setContentProvider(rentalProvider);
    rentalTreeViewer.setLabelProvider(rentalProvider);

    //
    rentalTreeViewer.setInput(Collections.singletonList(agency));
   
    provideSelection();
    
    providePopupMenu();
  }
  
  @Inject
  EMenuService menuService;
  
  private void providePopupMenu() {
	// TODO Auto-generated method stub
	  
	  menuService.registerContextMenu(rentalTreeViewer.getTree(), "com.sogeti.rental.eap.popupmenu");
}

@Inject
  private ESelectionService selectionService;
  
  private void provideSelection() {
	  rentalTreeViewer.addSelectionChangedListener(event -> {
		  IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		  selectionService.setSelection(selection.size() == 1? selection.getFirstElement() : selection.toArray());
		  
	  });
  }

  @Focus
  public void setFocus()
  {
  }
  
  /*
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "Rental Agency ViewPart";
  }
  
  @Inject
  public void refreshTree(
		  @Preference(value=PREF_CUSTOMERS_COLOR) String customerColor,
		  @Preference(value=PREF_RENTAL_COLOR) String rentalColor,
		  @Preference(value=PREF_OBJECTS_COLOR) String objectColor) {
	  //
	  if (rentalTreeViewer != null && ! rentalTreeViewer.getTree().isDisposed())
		  rentalTreeViewer.refresh();
  }
}
