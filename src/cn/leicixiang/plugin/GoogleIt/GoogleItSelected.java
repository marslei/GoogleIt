package cn.leicixiang.plugin.GoogleIt;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.openapi.util.TextRange;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class GoogleItSelected extends EditorAction {
    public GoogleItSelected(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }

    public GoogleItSelected() {
        this(new GoogleItSelected.GHandler());
    }

    private static class GHandler extends EditorWriteActionHandler {
        private GHandler() {
        }

        public void executeWriteAction(Editor editor, DataContext dataContext) {
            Document document = editor.getDocument();
            if(editor != null && document != null && document.isWritable()) {
                SelectionModel selectionModel = editor.getSelectionModel();
                TextRange charsRange = new TextRange(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd());
                String searchText = document.getText().substring(charsRange.getStartOffset(), charsRange.getEndOffset());
                if(searchText.length() >= 1) {
                    try {
                        String e = "https://www.google.com.hk/#newwindow=1&safe=strict&q=";
                        String encodedUrl = URLEncoder.encode(searchText.replaceAll("\n", ""), "UTF-8");
                        openURI((new URL(e + encodedUrl)).toURI());
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }

                }
            }
        }

        public static void openURI(URI uri) {
            Desktop desktop = Desktop.isDesktopSupported()?Desktop.getDesktop():null;
            if(desktop != null && desktop.isSupported(Action.BROWSE)) {
                try {
                    desktop.browse(uri);
                } catch (Exception var3) {
                    var3.printStackTrace();
                }
            }

        }
    }
}
