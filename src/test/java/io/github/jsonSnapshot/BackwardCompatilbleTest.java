package io.github.jsonSnapshot;

import static io.github.jsonSnapshot.SnapshotMatcher.*;
import static io.github.jsonSnapshot.SnapshotUtils.extractArgs;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BackwardCompatilbleTest {

  private FakeObject fakeObject = Mockito.mock(FakeObject.class);

  @BeforeAll
  public static void beforeAll() {
    start();
  }

  @AfterAll
  public static void afterAll() {
    validateSnapshots();
  }

  @Test // Snapshot any object
  public void shouldShowSnapshotExample() {
    expect("<any type of object>").toMatchSnapshot();
  }

  @Test // Snapshot arguments passed to mocked object (from Mockito library)
  public void shouldExtractArgsFromMethod() {
    fakeObject.fakeMethod("test1", 1L, Arrays.asList("listTest1"));
    fakeObject.fakeMethod("test2", 2L, Arrays.asList("listTest1", "listTest2"));

    expect(
            extractArgs(
                fakeObject,
                "fakeMethod",
                new SnapshotCaptor(String.class),
                new SnapshotCaptor(Long.class),
                new SnapshotCaptor(List.class)))
        .toMatchSnapshot();
  }

  @Test // Snapshot arguments passed to mocked object support ignore of fields
  public void shouldExtractArgsFromFakeMethodWithComplexObject() {
    FakeObject fake = new FakeObject();
    fake.setId("idMock");
    fake.setName("nameMock");

    // With Ignore
    fakeObject.fakeMethodWithComplexObject(fake);
    Object fakeMethodWithComplexObjectWithIgnore =
        extractArgs(
            fakeObject,
            "fakeMethodWithComplexObject",
            new SnapshotCaptor(Object.class, FakeObject.class, "name"));

    Mockito.reset(fakeObject);

    // Without Ignore of fields
    fakeObject.fakeMethodWithComplexObject(fake);
    Object fakeMethodWithComplexObjectWithoutIgnore =
        extractArgs(
            fakeObject,
            "fakeMethodWithComplexObject",
            new SnapshotCaptor(Object.class, FakeObject.class));

    expect(fakeMethodWithComplexObjectWithIgnore, fakeMethodWithComplexObjectWithoutIgnore)
        .toMatchSnapshot();
  }

  class FakeObject {

    private String id;

    private Integer value;

    private String name;

    void fakeMethod(String fakeName, Long fakeNumber, List<String> fakeList) {}

    void fakeMethodWithComplexObject(Object fakeObj) {}

    void setId(String id) {
      this.id = id;
    }

    void setName(String name) {
      this.name = name;
    }
  }
}
