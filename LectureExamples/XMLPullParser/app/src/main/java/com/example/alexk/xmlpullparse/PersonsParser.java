package com.example.alexk.xmlpullparse;

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by AlexK on 2/18/2018.
 */

public class PersonsParser {
    public static class PersonsSAXParser extends DefaultHandler {

        ArrayList<Person> persons;
        Person person;
        Address address;
        StringBuilder innerXML;

        static public ArrayList<Person> parsePersons(InputStream inputStream) throws IOException, SAXException {
            PersonsSAXParser parser = new PersonsSAXParser();
            Xml.parse(inputStream, Xml.Encoding.UTF_8, parser); //take input stream, generate events
            return parser.persons;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            this.persons = new ArrayList<>();
            this.innerXML = new StringBuilder();
        }

        @Override
        public void startElement(String uri, String localName, // name without prefix
                                 String qName, // name with prefix
                                 Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (localName.equals("person")) {
                person = new Person();
                person.id = Long.valueOf(attributes.getValue("id"));

            } else if (localName.equals("address")) {
                address = new Address();
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            String text = "";
            if(innerXML.toString() != null) {
                text = innerXML.toString().trim();
            }

            if (localName.equals("name")) {
                person.name = text;
            } else if (localName.equals("age")) {
                person.age = Integer.valueOf(text);
            } else if (localName.equals("line1")) {
                address.line1 = text;
            } else if (localName.equals("city")) {
                address.city = text;
            } else if (localName.equals("state")) {
                address.state = text;
            } else if (localName.equals("zip")) {
                address.zip = text;
                //Peron/object made below for association
            } else if (localName.equals("address")) {
                person.address = address;
            } else if (localName.equals("person")) {
                persons.add(person);
            }
            innerXML.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            innerXML.append(ch, start, length);
        }
    }

    public static class PersonsPullParcer{

        static public ArrayList<Person> parsePersons(InputStream inputStream) throws IOException, SAXException, XmlPullParserException {
            ArrayList<Person> persons = new ArrayList<>();
            Person person = null;
            Address address = null;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //factory.setNameSpaceAware(true);
            XmlPullParser parser = factory.newPullParser(); // Create parser
            parser.setInput(inputStream, "UTF-8"); // Pass it an input stream

            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {

                switch (event) {
                    case XmlPullParser.START_TAG: // Looking for start tag...

                        if (parser.getName().equals("person")) {
                            person = new Person();
                            person.id = Long.valueOf(parser.getAttributeValue(null, "id"));
                        } else if (parser.getName().equals("name")) {
                            person.name = parser.nextText().trim();
                        } else if (parser.getName().equals("age")) {
                            person.age = Integer.valueOf(parser.nextText().trim());
                        } else if (parser.getName().equals("address")) {
                            address = new Address();
                        } else if (parser.getName().equals("line1")) {
                            address.line1 = parser.nextText().trim();
                        } else if (parser.getName().equals("city")) {
                            address.city = parser.nextText().trim();
                        } else if (parser.getName().equals("state")) {
                            address.state = parser.nextText().trim();
                        } else if (parser.getName().equals("zip")) {
                            address.zip = parser.nextText().trim();
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("person")) {
                            persons.add(person);
                        } else if (parser.getName().equals("address")) {
                            person.address = new Address();
                        }
                        break;

                    default:
                        break;
                }

                event = parser.next();
            }

            return persons;
        }
    }
}
