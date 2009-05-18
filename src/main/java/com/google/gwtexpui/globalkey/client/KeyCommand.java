// Copyright (C) 2009 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gwtexpui.globalkey.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwtexpui.safehtml.client.SafeHtml;
import com.google.gwtexpui.safehtml.client.SafeHtmlBuilder;


public abstract class KeyCommand implements KeyPressHandler {
  public static final int M_CTRL = 1 << 16;
  public static final int M_ALT = 2 << 16;
  public static final int M_META = 4 << 16;
  private static final String KS = "gwtexpui-globalkey-KeyboardShortcuts-Key";

  public static boolean same(final KeyCommand a, final KeyCommand b) {
    return a.getClass() == b.getClass() && a.helpText.equals(b.helpText);
  }

  final int keyMask;
  private final String helpText;

  public KeyCommand(final int mask, final int key, final String help) {
    this(mask, (char) key, help);
  }

  public KeyCommand(final int mask, final char key, final String help) {
    assert help != null;
    keyMask = mask | key;
    helpText = help;
  }

  public String getHelpText() {
    return helpText;
  }

  SafeHtml describeKeyStroke() {
    final SafeHtmlBuilder b = new SafeHtmlBuilder();

    if ((keyMask & M_CTRL) == M_CTRL) {
      modifier(b, Util.C.keyCtrl());
    }
    if ((keyMask & M_ALT) == M_ALT) {
      modifier(b, Util.C.keyAlt());
    }
    if ((keyMask & M_META) == M_META) {
      modifier(b, Util.C.keyMeta());
    }

    final char c = (char) (keyMask & 0xffff);
    switch (c) {
      case KeyCodes.KEY_ENTER:
        namedKey(b, Util.C.keyEnter());
        break;
      case KeyCodes.KEY_ESCAPE:
        namedKey(b, Util.C.keyEsc());
        break;
      case KeyCodes.KEY_PAGEDOWN:
        namedKey(b, Util.C.keyPageDown());
        break;
      case KeyCodes.KEY_PAGEUP:
        namedKey(b, Util.C.keyPageUp());
        break;
      default:
        b.openSpan();
        b.setStyleName(KS);
        b.append(String.valueOf(c));
        b.closeSpan();
        break;
    }

    return b;
  }

  private void modifier(final SafeHtmlBuilder b, final String name) {
    namedKey(b, name);
    b.append(" + ");
  }

  private void namedKey(final SafeHtmlBuilder b, final String name) {
    b.append('<');
    b.openSpan();
    b.setStyleName(KS);
    b.append(name);
    b.closeSpan();
    b.append(">");
  }
}
