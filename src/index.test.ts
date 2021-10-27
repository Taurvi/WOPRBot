import { hello } from "./index";

describe("index", () => {
  describe("hello", () => {
    it("should return `helloWorld`", () => {
      // Given
      // When
      const response = hello();

      // Then
      expect(response).toEqual("Hello world!");
    });
  });
});
