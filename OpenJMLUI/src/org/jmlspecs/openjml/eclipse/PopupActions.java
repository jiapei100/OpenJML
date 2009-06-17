package org.jmlspecs.openjml.eclipse;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/** This class and its inner classes implement the various utils
 * that are defined when a right-mouse click is performed on menu items
 * in the Package Navigator and other similar Views.  The class names
 * must be in synch with the information in the plugin definition.
 */
abstract public class PopupActions implements IObjectActionDelegate {

    /** The Eclipse ID of the Decorator used on Java Projects to show that
     * JML compilation is enabled.  This must match the ID defined in the plugin 
     * definition.
     */
    static public final String JML_DECORATOR_ID = Activator.PLUGIN_ID + ".JMLDecoration";

    /** A cached value of the most recent selection */
    protected ISelection selection;

    /** A cached value of the shell */
    protected Shell shell;
    
    /** A cached value of the utilities object */
    protected Utils utils = Activator.getDefault().utils;

    /** The method that is called when the menu item is activated. */
    //JAVA16 @Override
    abstract public void run(IAction action);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    //JAVA16 @Override
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    //JAVA16 @Override
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        this.shell = targetPart.getSite().getShell();
    }


    /** This class implements the 'EnableNature' action, which adds the
     * JML Natures to a Java Project.  This menu item
     * is only enabled for Java Projects.
     */
    public static class EnableNature extends PopupActions {
        // This is all done in the UI thread with no progress
        @Override
        public final void run(final IAction action) {
            try {
                utils.changeJmlNatureSelection(true,selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"MenuActions.EnableJML",e);
            }
        }
    }

    /** This class implements the 'DeleteNature' action, which removes
     * the JML Nature from a Java Project.  This menu item
     * is only enabled for Java Projects.
     */
    public static class DisableNature extends PopupActions {
        // This is all done in the UI thread with no progress
        @Override
        public final void run(final IAction action) {
            try {
                utils.changeJmlNatureSelection(false,selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"MenuActions.DisableJML",e);
            }
        }
    }


    /** This class implements the action of deleting all the JML markers
     * ikn everything selected (recursively, for containers).  It is completed
     * in the UI thread, without a progress monitor.
     */
    public static class DeleteMarkers extends PopupActions {
        @Override
        public final void run(final IAction action) {
            try {
                utils.deleteMarkersInSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.DeleteMarkers",e);
            }
        }
    }

    /** This class implements the action of checking JML for
     *  a resource (recursively, for containers).
     */
    public static class CheckJML extends PopupActions {
        @Override
        public void run(IAction action) {
            try {
                utils.checkSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.CheckJML",e);
            }
        }
    }

    /** This class implements the action of checking JML for
     *  a resource (recursively, for containers).
     */
    public static class CheckESC extends PopupActions {
        @Override
        public void run(IAction action) {
            try {
                utils.checkESCSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.CheckESC",e);
            }

        }
    }

    /** This class implements the action of compiling runtime checks for
     *  a resource (recursively, for containers).
     */
    public static class RAC extends PopupActions {
        @Override
        public void run(IAction action) {
            try{
                utils.racSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.RAC",e);
            }
        }
    }

    /** This class implements the action of popping up a dialog to
     * show specifications of a Java element.
     */
    public static class ShowSpecs extends PopupActions {
        @Override
        public void run(IAction action) {
            try{
                utils.showSpecsForSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.ShowSpecs",e);
            }

        }
    }

    /**
     * This action opens an editor containing the specifications file
     * for the selected Java classes.
     * 
     * @author David Cok
     *
     */
    static public class SpecsEditor extends PopupActions {
        // This is done in the UI thread.
        @Override
        public final void run(final IAction action) {
            try {
                utils.openSpecEditorForSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"MenuActions.SpecsEditor",e);
            }
        }
    }


    /** This class implements the action of popping up a dialog to
     * show the counterexample for a Java method.
     */
    public static class ShowCounterexample extends PopupActions {
        @Override
        public void run(IAction action) {
            try {
                utils.showProofInfoForSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.ShowCounterexample",e);
            }
        }
    }


    /**
     * This action adds selected folders to the specs path.
     * @author David Cok
     */
    static public class AddToSpecsPath extends PopupActions {
        // This is done in the UI thread. 
        @Override
        public final void run(final IAction action) {
            try {
                utils.addSelectionToSpecsPath(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.AddToSpecsPath",e);
            }
        }
    }

    /**
     * This action removes selected folders from the specs path.
     * @author David Cok
     */
    static public class RemoveFromSpecsPath extends PopupActions {
        // This is done in the UI thread. 
        @Override
        public final void run(final IAction action) {
            try {
                utils.removeSelectionFromSpecsPath(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.RemoveFromSpecsPath",e);
            }
        }
    }

    /**
     * This action puts up a dialog that allows manipulation of the specs path.
     * @author David Cok
     */
    static public class SpecsPath extends PopupActions {
        // This is done in the UI thread. 
        @Override
        public final void run(final IAction action) {
            try {
                utils.manipulateSpecsPath(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.SpecsPath",e);
            }
        }
    }

    /**
     * This action puts up a dialog that allows manipulation of the class path.
     * @author David Cok
     */
    static public class ClassPath extends PopupActions {
        // This is done in the UI thread. 
        @Override
        public final void run(final IAction action) {
            try {
                utils.manipulateClassPath(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.ClassPath",e);
            }
        }
    }

    /**
     * This action generates jmldoc documentation for selected projects (and
     * for projects whose elements are selected).
     * @author David Cok
     */
    static public class JmlDoc extends PopupActions {
        // This is done in the UI thread. 
        @Override
        public final void run(final IAction action) {
            try {
                utils.jmldocSelection(selection,null,shell);
            } catch (Exception e) {
                utils.topLevelException(shell,"PopupActions.JmlDoc",e);
            }
        }
    }

}