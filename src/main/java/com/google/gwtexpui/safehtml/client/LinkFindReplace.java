// Copyright (C) 2013 The Android Open Source Project
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

package com.google.gwtexpui.safehtml.client;

/**
 * A Find/Replace pair whose replacement string is a link.
 * <p>
 * It is safe to pass arbitrary user-provided links to this class. Links are
 * sanitized as follows:
 * <ul>
 * <li>Only http(s) and mailto links are supported; any other scheme results in
 * an {@link IllegalStateException} from {@link #replace()}.
 * <li>Special characters in the replacement link are escaped with
 * {@link SafeHtmlBuilder}.</li>
 * </ul>
 */
public class LinkFindReplace implements FindReplace {
  public static boolean hasValidScheme(String link) {
    int colon = link.indexOf(':');
    if (colon < 0) {
      return true;
    }
    String scheme = link.substring(0, colon);
    return "http".equalsIgnoreCase(scheme)
        || "https".equalsIgnoreCase(scheme)
        || "mailto".equalsIgnoreCase(scheme);
  }

  private String find;
  private String link;

  protected LinkFindReplace() {
  }

  public LinkFindReplace(String find, String link) {
    this.find = find;
    this.link = link;
  }

  @Override
  public String find() {
    return find;
  }

  @Override
  public String replace() {
    if (!hasValidScheme(link)) {
      throw new IllegalStateException("Invalid scheme: " + toString());
    }
    return new SafeHtmlBuilder()
        .openAnchor()
        .setAttribute("href", link)
        .append(SafeHtml.asis(find))
        .closeAnchor()
        .asString();
  }

  @Override
  public String toString() {
    return "find = " + find + ", link = " + link;
  }
}
