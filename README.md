## Theater plays in Chicago

### What is it?

This is a web scraping based application that provides a list of theatrical events in Chicago, IL, and its closest suburbs.

You can filter events by:
- **Date**
- **Price**
- **Genres**

You can search events by part of title or description.

### How does it look like?



## How to run?

**Requirements**

- Docker
- Git

If you don't have Docker, you can download it [here](https://www.docker.com/products/docker-desktop).

**1. Clone the repository and navigate to the project directory**

```bash
$ git clone https://github.com/Kidchai/chicago-plays.git
$ cd chicago-plays
```

**2. Start the application and database using Docker**

```bash
$ docker-compose up
```

Now you need just open your web browser and navigate to http://localhost:5173.

### Stack

- **Server part**
  - Java, Spring MVC, Spring Data JPA, Spring Boot, PostgreSQL, H2, Maven, HtmlUnit, JUnit.
- **Client part**
  - Vue.js.
- **Deployment**
  - Docker.

## Credits

[chicagoplays.com](https://chicagoplays.com/) is a theatrical events data extraction source.