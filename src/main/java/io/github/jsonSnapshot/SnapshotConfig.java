package io.github.jsonSnapshot;

import io.github.jsonSnapshot.matchingstrategy.StringEqualsMatchingStrategy;

public interface SnapshotConfig {

  default String getFilePath() {
    return "src/test/java/";
  }

  default String getFileExtension() { return "snap"; }

  default SnapshotMatchingStrategy getSnapshotMatchingStrategy() {
    return StringEqualsMatchingStrategy.INSTANCE;
  }
}
