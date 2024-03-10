// Copyright Eric Chauvin 2022.



// The cell renderer is descended from a label.
// javax.swing.JLabel


import java.awt.Component;
import java.awt.Color;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Font;



public class FileTreeCellRender extends
                     DefaultTreeCellRenderer
  {
  // It needs to have a version UID since it's
  // serializable.
  public static final long serialVersionUID = 1;
  private MainApp mApp;
  private Font mainFont;

  private FileTreeCellRender()
    {

    }

  public FileTreeCellRender( // MainApp useApp,
                         Font useFont )
    {
    super();

    // mApp = useApp;
    mainFont = useFont;
    }


  @Override
  public Component getTreeCellRendererComponent(
                    JTree tree, Object value,
                    boolean sel, boolean exp,
                    boolean leaf, int row,
                    boolean hasFocus )
    {
    super.getTreeCellRendererComponent(
                        tree, value, sel, exp,
                        leaf, row, hasFocus );

    // Make sure this is cast to the right kind
    // of object!
//    String node = (String)
  //                ((DefaultMutableTreeNode)
  //                       value).getUserObject();


  setFont( mainFont );

  setOpaque( true );
  setForeground( Color.white );
  setBackground( Color.black );

    // if (leaf && node.toString().endsWith(
       //    "thisAndThat"))
      // setForeground( Color.red );


    return this;
    }
  }
