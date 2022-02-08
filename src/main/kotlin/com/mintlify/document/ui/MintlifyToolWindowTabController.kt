// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.mintlify.document.ui

import com.intellij.openapi.util.Key

interface MintlifyToolWindowTabController {

  companion object {
    val KEY = Key.create<MintlifyToolWindowTabController>("Mintlify.Document.ToolWindow.Tab.Controller")
  }
}