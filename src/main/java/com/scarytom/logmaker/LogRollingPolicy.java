package com.scarytom.logmaker;

import java.io.File;

import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.rolling.TriggeringPolicy;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.RenameUtil;
import ch.qos.logback.core.spi.ContextAwareBase;

public final class LogRollingPolicy<E> extends ContextAwareBase implements RollingPolicy, TriggeringPolicy<E> {

    private final RenameUtil renameUtil = new RenameUtil();

    private boolean started;
    private FileAppender<?> parent;

    public boolean isStarted() {
        return started;
    }

    public void start() {
        renameUtil.setContext(this.context);
        started = true;
    }

    public void stop() {
        started = false;
    }

    @Override
    public boolean isTriggeringEvent(File activeFile, E event) {
        final String bufferFileName = getBufferFileName();
        if (bufferFileName != null) {
            return !new File(bufferFileName).exists();
        }
        return false;
    }

    @Override
    public void rollover() throws RolloverFailure {
        final String activeFileName = getActiveFileName();
        final String bufferFileName = getBufferFileName();
        if (activeFileName != null && bufferFileName != null) {
            renameUtil.rename(activeFileName, bufferFileName);
        }
    }

    @Override
    public String getActiveFileName() {
        return parent.rawFileProperty();
    }

    public String getBufferFileName() {
        return getActiveFileName() == null ? null : getActiveFileName() + ".top";
    }

    @Override
    public CompressionMode getCompressionMode() {
        return CompressionMode.NONE;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void setParent(FileAppender appender) {
        this.parent = appender;
    }
}
