package com.objectify.datastore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class XMLAdapter implements DataStore {

    private static XMLAdapter xmlAdapterInstance = null;
    private Path xmlPath;

    private XMLAdapter(){

    }

    public static synchronized XMLAdapter getInstance(){
        if (xmlAdapterInstance == null){
            xmlAdapterInstance = new XMLAdapter();
        }
        return xmlAdapterInstance;
    }

    @Override
    public <T> void writeData(String xmlFileName, ArrayList<T> data) throws IOException {
        try {
            initializeXMLData(xmlFileName);

            XMLWrapper<T> wrapper = new XMLWrapper<>(data);
            JAXBContext context = JAXBContext.newInstance(wrapper.getClass(), data.get(0).getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, new File(xmlPath.toString()));
        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> ArrayList<T> readData(String xmlFileName, Class<T> valueType) throws IOException {
        try {
            initializeXMLData(xmlFileName);
            JAXBContext context = JAXBContext.newInstance(XMLWrapper.class, valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XMLWrapper<T> wrapper = (XMLWrapper<T>) unmarshaller.unmarshal(new File(xmlPath.toString()));
            return wrapper.getData();
        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteData() throws IOException {
        Files.delete(xmlPath);
    }

    @Override
    public <T> void saveData(String xmlFileName, ArrayList<T> data) throws IOException{
        try {
            ArrayList<T> existingData = readData(xmlFileName, (Class<T>) data.get(0).getClass());
            existingData.addAll(data);
            writeData(xmlFileName, existingData);
        } catch (IOException e) {
            writeData(xmlFileName, data);
        }
    }

    public void initializeXMLData(String xmlFileName) throws Exception {
        this.xmlPath = Paths.get("src", "resources", "XML", xmlFileName);
        if(!Files.exists(this.xmlPath)) {
            try {
                Files.createFile(this.xmlPath);
            } catch (IOException e) {
                throw new IOException("Failed to create file " + xmlFileName);
            }
        }
    }

    @XmlRootElement(name = "XMLWrapper")
    public static class XMLWrapper<T> {

        private ArrayList<T> data;

        public XMLWrapper() {
            data = new ArrayList<>();
        }

        public XMLWrapper(ArrayList<T> data) {
            this.data = data;
        }

        @XmlElement(name = "Elements")
        public ArrayList<T> getData() {
            return data;
        }

        public void setData(ArrayList<T> data) {
            this.data = data;
        }
    }
}
