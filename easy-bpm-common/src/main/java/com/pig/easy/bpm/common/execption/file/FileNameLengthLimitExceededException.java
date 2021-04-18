package com.pig.easy.bpm.common.execption.file;

/**
 * 文件名称超长限制异常类
 * 
 * @author pig
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}
