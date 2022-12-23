package com.ei10391048.project.modelo.information;

public class NewsInformation extends APIInformation{
    String author;

    String title;

    String description;

    String newsURL;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    @Override
    public String toString() {
        return "NewsInformation{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", newsURL='" + newsURL + '\'' +
                ", apiName='" + apiName + '\'' +
                ", locationName='" + locationName + '\'' +
                ", date='" + date + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
