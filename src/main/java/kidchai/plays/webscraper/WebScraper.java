package kidchai.plays.webscraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import kidchai.plays.model.Event;
import kidchai.plays.model.Genre;
import kidchai.plays.repository.EventRepository;
import kidchai.plays.repository.GenreRepository;
import kidchai.plays.util.DateFormatterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebScraper {
    private HtmlPage page;
    WebClient client;
    private String searchUrl;
    private final EventRepository eventRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public WebScraper(EventRepository eventRepository, GenreRepository genreRepository) {
        this.eventRepository = eventRepository;
        this.genreRepository = genreRepository;
        client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        searchUrl = "https://chicagoplays.com/find-a-show/?vemsearch%5Blisting%5D=1614&vemsearch%5Bstart%5D=&vemsearch%5Bend%5D=";
    }

    public void saveEvents() {
        fillEventsFromOnePage();
        var nextPage = getNextPageURL();

        while (nextPage != null) {
            fillEventsFromOnePage();
            nextPage = getNextPageURL();
        }
    }

    private void fillEventsFromOnePage() {
        if (searchUrl == null)
            return;

        try {
            page = client.getPage(searchUrl);
            var eventsNodeList = page.querySelectorAll(".vem-single-event");

            for (var eventNode : eventsNodeList) {
                getEventFromNode(eventNode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getEventFromNode(DomNode eventNode) {
        Event event = new Event();

        var domUrl = (DomElement) eventNode.querySelector(".vem-single-event-thumbnail > a").getFirstChild();
        var stringUrl = domUrl.getAttribute("src");

        var domTitle = eventNode.querySelector(".vem-single-event-title");
        var stringTitle = domTitle == null ? null : domTitle.getTextContent();

        var domTheatre = eventNode.querySelector(".vem-single-event-theatre-name");
        var stringTheatre = domTheatre == null ? null : domTheatre.getTextContent();

        var domGenres = eventNode.querySelector(".vem-single-event-genres");
        var stringGenres = domGenres == null ? null : domGenres.getTextContent();
        var genres = getGenresFromString(stringGenres);

        for (Genre genre : genres) {
            event.addGenre(genre);
            genre.getEvents().add(event);
        }

        setEventDates(eventNode, event);

        var domDescription = eventNode.querySelector(".vem-single-event-excerpt");
        var stringDescription = domDescription == null ? null : domDescription.getTextContent();

        var domEventUrl = (DomElement) eventNode.querySelector(".vem-single-event-thumbnail").getFirstChild();
        var stringEventUrl = domEventUrl == null ? null : domEventUrl.getAttribute("href");

        var domPrice = (DomElement) eventNode.querySelector(".vem-occurrences");
        if (domPrice != null)
            domPrice = (DomElement) domPrice.getFirstChild().querySelector(".vem-single-event-date-ticket-pricing");
        var stringPrice = domPrice == null ? null : domPrice.getTextContent();

        var domNextShow = (DomElement) eventNode.querySelector(".vem-single-event-date-start");
        var stringNextShow = domNextShow == null ? null : domNextShow.getTextContent();
        var dateNextShow = DateFormatterUtil.parseToLocalDateTime(stringNextShow);

        event.setImageUrl(stringUrl);
        event.setTitle(stringTitle);
        event.setTheatre(stringTheatre);

        event.setDescription(stringDescription);
        event.setEventUrl(stringEventUrl);
        event.setPrice(stringPrice);
        event.setNextShow(dateNextShow);

        eventRepository.save(event);
        genreRepository.saveAll(genres);
    }

    private List<Genre> getGenresFromString(String stringGenres) {
        List<Genre> genres = new ArrayList<>();
        String[] genresArray;
        Genre thisGenre;

        if (stringGenres.length() != 0) {
            genresArray = stringGenres.split(", ");
            for (var genre : genresArray) {
                thisGenre = genreRepository.findByGenre(genre);
                if (thisGenre == null) {
                    thisGenre = new Genre(genre);
                }
                genres.add(thisGenre);
            }
        }
        return genres;
    }

    private void setEventDates(DomNode eventNode, Event event) {
        var domDates = eventNode.querySelector(".vem-single-event-run-dates > span");

        var stringFirstDate = getTextContent(domDates.querySelector(".vem-earliest"));
        var stringLastDate = getTextContent(domDates.querySelector(".vem-latest"));

        event.setFirstDate(parseToDate(stringFirstDate));
        event.setLastDate(stringLastDate == null ? event.getFirstDate() : parseToDate(stringLastDate));
    }

    private String getTextContent(DomElement domElement) {
        return domElement == null ? null : domElement.getTextContent();
    }

    private LocalDateTime parseToDate(String date) {
        return DateFormatterUtil.parseToDateTime(date).atStartOfDay();
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
