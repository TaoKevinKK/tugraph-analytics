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

package com.antgroup.geaflow.common.shuffle;

public enum BatchPhase {

    /**
     * Execute the shuffle process in pull mode.
     */
    CLASSIC,
    /**
     * Read the pre-fetched shuffle data.
     */
    PREFETCH_READ,
    /**
     * Pre-fetch the data to the reduce side.
     */
    PREFETCH_WRITE

}
