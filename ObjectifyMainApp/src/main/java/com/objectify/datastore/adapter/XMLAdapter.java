package com.objectify.datastore.adapter;

import com.objectify.datastore.interfaces.DataStore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class XMLAdapter<T> implements DataStore<T> {
    private final Class<T> cls;
    private Path xmlPath;
    private final String filename;

    public XMLAdapter(String filename, Class<T> cls) {
        if (!filename.endsWith(".xml")) {
            filename += ".xml";
        }
        xmlPath = Paths.get("ObjectifyMainApp","src", "resources", "XML", filename);
        this.filename = filename;
        this.cls = cls;
        initializeFile(filename);
    }

    @Override
    public void write(T data) {
        try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(data, new File(xmlPath.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> read() {
        try {
            JAXBContext context = JAXBContext.newInstance(cls);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T result = (T) unmarshaller.unmarshal(xmlPath.toFile());
            return Optional.of(result);
        } catch (JAXBException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void delete() {
        try {
            Files.deleteIfExists(xmlPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getXmlPath() {
        return xmlPath;
    }


    private void initializeFile(String filename) {
        Path resPath = Paths.get("datastore", "resources", "XML");
        try {
            if (!Files.exists(resPath)) {
                Files.createDirectories(resPath);
            }
            xmlPath = Paths.get(resPath.toString(), filename);
            if (!Files.exists(xmlPath)) {
                Files.createFile(xmlPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create XML file: " + e.getMessage());
        }
    }
}
