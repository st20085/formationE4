package com.sogeti.rental.ui.views;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.opcoach.training.rental.RentalAgency;

public class RentalAgencyViewPart//extends ViewPart implements RentalUIConstants
{
  TreeViewer rentalTreeViewer;

  public RentalAgencyViewPart()
  {
  }
  

  @PostConstruct
  public void createPartControl(Composite parent, RentalAgency agency)
  {
//    GridLayout gl_parent = new GridLayout(1, false);
//    gl_parent.verticalSpacing = 1;
//    gl_parent.marginWidth = 0;
//    gl_parent.marginHeight = 0;
//    parent.setLayout(gl_parent);
//    
//    Composite toolComposite = new Composite(parent, SWT.NONE);
//    RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
//    toolComposite.setLayout(rl_composite);
//    
//    final Button expandButton = new Button(toolComposite, SWT.NONE);
//    expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_EXPAND_ALL));
//    
//    expandButton.addListener(SWT.Selection, new Listener()
//    {
//      boolean expand = false;
//      
//      @Override
//      public void handleEvent(Event event)
//      {
//        if (expand)
//        {
//          rentalTreeViewer.expandAll();
//          expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_COLLASPSE_ALL));
//        }
//        else
//        {
//          rentalTreeViewer.collapseAll();
//          expandButton.setImage(RentalUIActivator.getDefault().getImageRegistry().get(ICON_EXPAND_ALL));
//        }
//        expand = !expand;
//      }
//    });
    
    
    
    //
    rentalTreeViewer = new TreeViewer(parent);
    Tree tree = rentalTreeViewer.getTree();
    tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

    RentalProvider rentalProvider = new RentalProvider();
    rentalTreeViewer.setContentProvider(rentalProvider);
    rentalTreeViewer.setLabelProvider(rentalProvider);

    //
    rentalTreeViewer.setInput(Collections.singletonList(agency));
   
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
}
