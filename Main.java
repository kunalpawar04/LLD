package org.example;

/*
Visitor Design Pattern:
---------------------------------------
- Allows adding new operations on objects without modifying their classes
- Separates algorithm (Visitor) from object structure (Elements)
- Uses double dispatch → element decides which visitor method to call
- Helps follow Open/Closed Principle (open for extension, closed for modification)

- We separate:
  1. Object structure (Image, Video, PDF)
  2. Operations (Render, Compress, Size)

- Key idea:
  → Object accepts a visitor
  → Visitor decides what operation to perform

In this example:
- DocumentElement (Image, Video, PDF) are elements
- FileVisitor is the visitor interface
- RenderVisitor, CompressVisitor, SizeVisitor are concrete visitors
- Each element accepts a visitor and calls the appropriate visit() method

Key Benefits:
- Easy to add new operations (just create a new Visitor)
- No need to modify existing element classes

Drawbacks:
- Adding new element types (e.g., Audio) requires modifying all visitors
- Can become hard to maintain if element types change frequently
*/

interface FileVisitor {
    // Each method handles a specific type of object
    void visit(Image image);
    void visit(Video video);
    void visit(PDF pdf);
}

/*
Concrete Visitor 1 → Rendering logic
*/
class RenderVisitor implements FileVisitor {
    @Override
    public void visit(Image image) {
        System.out.println("Rendering the image: " + image.getName());
    }

    @Override
    public void visit(Video video) {
        System.out.println("Rendering the video: " + video.getName());
    }

    @Override
    public void visit(PDF pdf) {
        System.out.println("Rendering the pdf: " + pdf.getName());
    }
}

/*
Concrete Visitor 2 → Compression logic
*/
class CompressVisitor implements FileVisitor {
    @Override
    public void visit(Image image) {
        System.out.println("Compressing the image: " + image.getName());
    }

    @Override
    public void visit(Video video) {
        System.out.println("Compressing the video: " + video.getName());
    }

    @Override
    public void visit(PDF pdf) {
        System.out.println("Compressing the pdf: " + pdf.getName());
    }
}

/*
Concrete Visitor 3 → Size calculation logic
*/
class SizeVisitor implements FileVisitor {
    @Override
    public void visit(Image image) {
        System.out.println("Calculating the size of image: " + image.getName());
    }

    @Override
    public void visit(Video video) {
        System.out.println("Calculating the size of video: " + video.getName());
    }

    @Override
    public void visit(PDF pdf) {
        System.out.println("Calculating the size of pdf: " + pdf.getName());
    }
}

/*
Element Interface
- Any object that can be visited implements this
*/
interface DocumentElement {
    void accept(FileVisitor visitor);
}

/*
Concrete Element → Image
*/
class Image implements DocumentElement {
    private String name;

    public Image(String name) {
        this.name = name;
    }

    @Override
    public void accept(FileVisitor visitor) {
        // Pass itself to visitor → enables correct method call
        visitor.visit(this);  // double dispatch
    }

    public String getName() {
        return this.name;
    }
}

/*
Concrete Element → Video
*/
class Video implements DocumentElement {
    private String name;

    public Video(String name) {
        this.name = name;
    }

    @Override
    public void accept(FileVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return this.name;
    }
}

/*
Concrete Element → PDF
*/
class PDF implements DocumentElement {
    private String name;

    public PDF(String name) {
        this.name = name;
    }

    @Override
    public void accept(FileVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return this.name;
    }
}

/*
Client Code
- We create objects
- Pass different visitors to perform different operations
*/
public class Main {
    public static void main(String[] args) {

        Image image = new Image("picture.jpg");
        image.accept(new RenderVisitor());    // Render operation
        image.accept(new CompressVisitor());  // Compress operation
        image.accept(new SizeVisitor());      // Size calculation

        Video video = new Video("movie.mp4");
        video.accept(new CompressVisitor());

        PDF pdf = new PDF("report.pdf");
        pdf.accept(new RenderVisitor());
    }
}