#ifndef _cf_vbnm_FileChooser_C
#define _cf_vbnm_FileChooser_C

#ifndef _SHLOBJ_H_
#include <shlobj.h>
#endif

#include "cf_vbnm_filechooser4j_FileChooser.h"

#ifdef __cplusplus
extern "C"
{
#endif

#define jenv (*env)

    jbyte *select_file(jbyte *path, u_int length, jboolean method, const jbyte *filter, const jbyte *initial_dir, const jbyte *title, jint flags)
    {
        OPENFILENAME open;                       // 公共对话框结构。
                                                 //    char path[MAX_PATH];// 用来保存获取文件名称的缓冲区。
        ZeroMemory(&open, sizeof(OPENFILENAME)); // 初始化选择文件对话框
        open.lStructSize = sizeof(OPENFILENAME); //指定这个结构的大小，以字节为单位。
        open.lpstrFile = path;                   //打开的文件的全路径

        open.lpstrFile[0] = '\0';           //第一个字符串是过滤器描述的显示字符串
        open.nMaxFile = length;             //指定lpstrFile缓冲的大小，以TCHARs为单位
        open.lpstrFilter = filter;          //"位图(*.bmp)\0*.bmp\0所有文件(*.*)\0*.*\0\0";
                                            //打开文件类型
        open.nFilterIndex = 1;              //指定在文件类型控件中当前选择的过滤器的索引
        open.lpstrFileTitle = NULL;         // 指向接收选择的文件的文件名和扩展名的缓冲（不带路径信息）。这个成员可以是NULL。
        open.nMaxFileTitle = 0;             //指定lpstrFileTitle缓冲的大小，以TCHARs为单位
        open.lpstrInitialDir = initial_dir; //指向以空字符结束的字符串，可以在这个字符串中指定初始目录。
        open.Flags = flags;                 //OFN_EXPLORER | OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST; //位标记的设置，你可以使用来初始化对话框
        open.lpstrTitle = title;
        //GetOpenFileName (&open) ;//打开文件对话框
        //GetSaveFileName(&open);//保存文件对话框

        if (method)
        {
            if (GetOpenFileNameA(&open)) //保存文件对话框
                return path;
        }
        else
        {
            if (GetSaveFileNameA(&open)) // 显示打开选择文件对话框。
                return path;
        }

        return NULL;
    }
#define getThisObjField(name, sig) \
    jenv->GetObjectField(env, this, jenv->GetFieldID(env, jenv->GetObjectClass(env, this), name, sig))
#define getByteArrayEles(array, isCopy) jenv->GetByteArrayElements(env, array, isCopy)
#define getFieldID(name, sig) jenv->GetFieldID(env, jenv->GetObjectClass(env, this), name, sig)
    /**
 * Java_cf_vbnm_FileChooser_showOpenDialog0
 * 调用Windows文件选择器
*/
    // (boolean readonly, byte[] filenameFilter, byte[] path, byte[] initialDir, byte[] title, int flags)
    JNIEXPORT jshort JNICALL Java_cf_vbnm_filechooser4j_FileChooser_showOpenDialog0(
        JNIEnv *env, jobject this, jboolean readonly, jbyteArray filename_filter,
        jbyteArray path, jbyteArray initial_dir, jbyteArray title, jint flags)
    {
        jboolean isCopy = JNI_TRUE;

        const jbyte *extension = getByteArrayEles(filename_filter, &isCopy);
        //获取对象path数组
        jbyteArray pathobj = getThisObjField("path", "[B");

        jsize pathLen = jenv->GetArrayLength(env, pathobj);
        // jbyte *path = jenv->GetByteArrayElements(env, pathobj, &isCopy);

        jbyte *tmp_path = calloc(pathLen, sizeof(jbyte));

        //(jbyte *path, u_int length, jboolean method, const jbyte *filter, const jbyte *initial_dir, const jbyte *title, jint flags)
        if (select_file(tmp_path, sizeof(jbyte) * pathLen, readonly, extension, jenv->GetByteArrayElements(env, initial_dir, &isCopy), getByteArrayEles(title, &isCopy), flags))
        {
            jenv->SetByteArrayRegion(env, pathobj, 0, pathLen, tmp_path);
            free(tmp_path);
            return cf_vbnm_filechooser4j_FileChooser_NO_ERROR;
        }
        free(tmp_path);
        DWORD error_code = CommDlgExtendedError();
        return (error_code == FALSE) ? cf_vbnm_filechooser4j_FileChooser_ERR_USERCANCELED : error_code;
    }
#ifdef __cplusplus
}
#endif
#endif