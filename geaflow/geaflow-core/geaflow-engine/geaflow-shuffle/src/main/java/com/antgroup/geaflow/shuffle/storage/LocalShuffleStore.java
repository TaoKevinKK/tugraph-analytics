/*
 * Copyright 2023 AntGroup CO., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.shuffle.storage;

import static com.antgroup.geaflow.common.config.keys.ExecutionConfigKeys.JOB_WORK_PATH;

import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import com.antgroup.geaflow.shuffle.config.ShuffleConfig;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LocalShuffleStore implements ShuffleStore {
    private static final int BUFFER_SIZE = 64 * 1024;
    private static final String DEFAULT_LOCAL_ROOT = "/shuffle";

    private final String shufflePath;

    public LocalShuffleStore() {
        String workPath = ShuffleConfig.getInstance().getConfig().getString(JOB_WORK_PATH);
        Path path = Paths.get(workPath, DEFAULT_LOCAL_ROOT);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new GeaflowRuntimeException(e);
            }
        }
        this.shufflePath = path.toString();
    }

    @Override
    public String getFilePath(String fileName) {
        return Paths.get(shufflePath, fileName).toString();
    }

    @Override
    public InputStream getInputStream(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return new BufferedInputStream(Files.newInputStream(path, StandardOpenOption.READ), BUFFER_SIZE);
        } catch (IOException e) {
            throw new GeaflowRuntimeException(e);
        }
    }

    @Override
    public OutputStream getOutputStream(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE), BUFFER_SIZE);
        } catch (IOException e) {
            throw new GeaflowRuntimeException(e);
        }
    }
}
