package kidchai.plays.webscraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import kidchai.plays.models.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    private final List<Event> eventList;
    private HtmlPage page;
    WebClient client;
    String searchUrl;

    {
        client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        searchUrl = "https://chicagoplays.com/find-a-show/?vemsearch%5Blisting%5D=1366&vemsearch%5Bkeyword%5D=&vemsearch%5Bstart%5D=10%2F10%2F2022&vemsearch%5Bend%5D=10%2F27%2F2022&vemsearch%5Bgenre%5D=0&vemsearch%5Bneighborhood%5D=0&vemsearch%5Btheatre%5D=0&p1366=1";
        eventList = new ArrayList<>();
    }

    public List<Event> getEventList() {
        fillEventsFromOnePage();
        var nextPage = getNextPageURL();

        while (nextPage != null) {
            fillEventsFromOnePage();
            nextPage = getNextPageURL();
        }
        return eventList;
    }

    private void fillEventsFromOnePage() {
        try {
            page = client.getPage(searchUrl);
            var eventsNodeList = page.querySelectorAll(".vem-single-event");

            for (var eventNode : eventsNodeList) {
                var event = getEvent(eventNode);
                eventList.add(event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Event getEvent(DomNode eventNode) {
        Event event = new Event();

        var imageURLParent = eventNode.querySelector(".vem-single-event-thumbnail > a");
        DomElement imageURL = (DomElement) imageURLParent.getFirstChild();
        var link = imageURL.getAttribute("src");
        event.setImageUrl(link);

        var title = eventNode.querySelector(".vem-single-event-title");
        event.setTitle(title.getTextContent());

        var theatre = eventNode.querySelector(".vem-single-event-theatre-name");
        var theatreValue = theatre == null ? null : theatre.getTextContent();
        event.setTheatre(theatreValue);

        var genres = eventNode.querySelector(".vem-single-event-genres");
        event.setGenres(genres.getTextContent());

        var dates = eventNode.querySelector(".vem-single-event-run-dates > span");
        var earliestDate = dates.querySelector(".vem-earliest");
        var latestDate = dates.querySelector(".vem-latest");
        var earliestDateValue = earliestDate == null  ? null : earliestDate.getTextContent();
        var latestDateValue = latestDate == null  ? null : latestDate.getTextContent();
        event.setEarliestDate(earliestDateValue);
        event.setLatestDate(latestDateValue);

        var description = eventNode.querySelector(".vem-single-event-excerpt");
        event.setDescription(description.getTextContent());

        var detailsParent = eventNode.querySelector(".vem-single-event-thumbnail");
        DomElement detailsDomElement = (DomElement) detailsParent.getFirstChild();
        var details = detailsDomElement.getAttribute("href");
        event.setDetails(details);

        return event;
    }

    private String getNextPageURL() {
        var nextPage = (DomElement) page.querySelector(".vem-page-next");
        searchUrl = nextPage == null ? null : nextPage.getAttribute("href");
//        searchUrl = nextPage.getAttribute("href");
        if (searchUrl != null) {
            try {
                page = client.getPage(searchUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return searchUrl;
    }
}
