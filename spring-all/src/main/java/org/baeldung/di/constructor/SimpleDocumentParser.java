package org.baeldung.di.constructor;

public class SimpleDocumentParser {

    private DocumentReader documentReader;

    public SimpleDocumentParser(final DocumentReader documentReader) {
        System.out.println("simple document parser");
        this.documentReader = documentReader;
    }
}
