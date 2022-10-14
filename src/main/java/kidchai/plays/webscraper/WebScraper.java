package kidchai.plays.webscraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import kidchai.plays.model.Event;

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
        searchUrl = "https://chicagoplays.com/find-a-show/?vemsearch%5Blisting%5D=1614&vemsearch%5Bstart%5D=&vemsearch%5Bend%5D=";
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

        var imageUrl = (DomElement) eventNode.querySelector(".vem-single-event-thumbnail > a").getFirstChild();

        var link = imageUrl.getAttribute("src");
        event.setImageUrl(link);

        var title = eventNode.querySelector(".vem-single-event-title");
        var titleValue = title == null ? null : title.getTextContent();
        event.setTitle(titleValue);

        var theatre = eventNode.querySelector(".vem-single-event-theatre-name");
        var theatreValue = theatre == null ? null : theatre.getTextContent();
        event.setTheatre(theatreValue);

        var genres = eventNode.querySelector(".vem-single-event-genres");
        var genresValue = genres == null ? null : genres.getTextContent();
        event.setGenres(genresValue);

        var dates = eventNode.querySelector(".vem-single-event-run-dates > span");
        var earliestDate = dates.querySelector(".vem-earliest");
        var latestDate = dates.querySelector(".vem-latest");
        var earliestDateValue = earliestDate == null ? null : earliestDate.getTextContent();
        var latestDateValue = latestDate == null ? null : latestDate.getTextContent();
        event.setEarliestDate(earliestDateValue);
        event.setLatestDate(latestDateValue);

        var description = eventNode.querySelector(".vem-single-event-excerpt");
        var descriptionValue = description == null ? null : description.getTextContent();
        event.setDescription(descriptionValue);

        var eventUrl = (DomElement) eventNode.querySelector(".vem-single-event-thumbnail").getFirstChild();
        var eventUrlValue = eventUrl == null ? null : eventUrl.getAttribute("href");
        event.setEventUrl(eventUrlValue);

        var price = (DomElement) eventNode.querySelector(".vem-occurrences");
        if (price != null) {
            price = (DomElement) price.getFirstChild().querySelector(".vem-single-event-date-ticket-pricing");
        }
        var priceValue = price == null ? null : price.getTextContent();
        event.setPrice(priceValue);
        System.out.println("Price is" + priceValue);

        return event;
    }

    private String getNextPageURL() {
        var nextPage = (DomElement) page.querySelector(".vem-page-next");
        searchUrl = nextPage == null ? null : nextPage.getAttribute("href");
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
