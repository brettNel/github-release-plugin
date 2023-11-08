package org.jenkinsci.plugins.github.release;

import java.util.Comparator;

abstract class AbstractReleaseComparator implements Comparator<Release> {
  boolean ascending = true;

  abstract protected int doCompare(Release o1, Release o2);

  @Override
  public int compare(Release o1, Release o2) {
    return doCompare(o1, o2) * (ascending ? 1 : -1);
  }
}
