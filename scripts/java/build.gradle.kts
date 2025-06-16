/*
 * ******************************************************************
 * Copyright (c) 2025 Broadcom. All Rights Reserved.
 * The term "Broadcom" refers to Broadcom Inc.
 * and/or its subsidiaries.
 *
 * SPDX-License-Identifier: Apache-2.0
 * ******************************************************************
 */

plugins {
    eclipse
    idea
}

allprojects {
    apply(plugin = "eclipse")

    eclipse {
        if (hasProperty("eclipse.classpath.vars")) {
            pathVariables(mapOf("API_SPECS_GRADLE_USER_HOME" to file(gradle.gradleUserHomeDir)))
        }
    }
}

