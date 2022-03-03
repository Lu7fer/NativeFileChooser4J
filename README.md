# NativeFileChooser4J

Windows native file chooser for Java.

Support different charset.

## usage

```java
public class Test {
    public static void main(String[] args) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setReadonly(true).setPathLength(4096).setFilenameFilter("All Files 所有文件\0*.*\0\0")
                .setTitle("选择文件").setFlags(fileChooser.getFlags() | FileChooser.OFN_ALLOWMULTISELECT);
        System.out.println(fileChooser.openFileName() + "||" + fileChooser.getErrorCode());
        System.out.println(fileChooser.getPaths());
        System.out.println(fileChooser);
    }
}
```