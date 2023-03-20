package com.sangeng;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {
      testStream();
      testArr();
      testMap();
      testSort();
      testSortAuthor();
      testSortSkip();
      testFlatMap();
      testFlatMapCategory();
      testAuthor();
      testCollectorAuthor();
    }
    //获取一个存放所有作者名字的List集合。
    private static void testCollectorAuthor() {
        List<Author> authors = getAuthors();
        List<String> collect = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(collect);

    }

    //	输出所有作家的名字
    private static void testAuthor() {
        List<Author> authors = getAuthors();
        authors.stream()
                .map( author -> author.getName())
                .distinct()
                .forEach(authorName-> System.out.println("author = " + authorName));

    }

    //打印现有数据的所有分类。要求对分类进行去重。不能出现这种格式：哲学,爱情
    private static void testFlatMapCategory() {
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(new Function<Author, Stream<Book>>() {
                    @Override
                    public Stream<Book> apply(Author author) {
                        return author.getBooks().stream();
                    }
                })
                .distinct()
                .flatMap((Function<Book, Stream<?>>) book ->
                        Arrays.stream(book.getCategory().split(",")))
                .distinct()
                .forEach(category-> System.out.println("category = " + category));
    }

    //打印所有书籍的名字。要求对重复的元素进行去重。
    private static void testFlatMap() {
        List<Author> authors = getAuthors();
        authors.stream()
               .flatMap((Function<Author, Stream<Book>>) author
                       -> author.getBooks().stream())
                .distinct()
                .forEach(book -> System.out.println(book.getName()));
    }


    //打印除了年龄最大的作家外的其他作家，要求不能有重复元素，并且按照年龄降序排序。
    private static void testSortSkip() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .skip(1)
                .forEach(author -> System.out.println("author = " + author.getName()));
    }

    //	对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素,然后打印其中年龄最大的两个作家的姓名
    private static void testSortAuthor() {
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge()- o1.getAge())
                .limit(2)
                .forEach(author -> System.out.println("author = " + author.getName()));
    }

    //升序排列age
    private static void testSort() {
        List<Author> authors = getAuthors();
        authors.stream().distinct()
                .sorted((o1, o2) -> o1.getAge()- o2.getAge())
                .forEach(author -> System.out.println("author = " + author.getAge()));
    }

    private static void testMap() {
        Map<String,Integer> map = new HashMap<>();
        map.put("蜡笔小新",19);
        map.put("黑子",17);
        map.put("日向翔阳",16);
        Set<Map.Entry<String, Integer>> set = map.entrySet();
        set.stream().forEach(stringIntegerEntry -> System.out.println("stringIntegerEntry = " + stringIntegerEntry));
    }

    public static void testArr(){
        Integer[] arr = {1,2,3,4,5};
        Stream<Integer> stream = Arrays.stream(arr);
        stream.forEach(integer -> System.out.println("integer = " + integer));
    }

    public static void  testStream(){
        List<Author> authors = getAuthors();
        //打印所有年龄小于18的作家的名字
        authors.stream()
                .distinct()
                .filter(author-> author.getAge()<18)
                .forEach(author->
                        System.out.println("author = " + author.getName()));
    }
    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L,"蒙多",33,"一个从菜刀中明悟哲理的祖安人",null);
        Author author2 = new Author(2L,"亚拉索",15,"狂风也追逐不上他的思考速度",null);
        Author author3 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);
        Author author4 = new Author(3L,"易",14,"是这个世界在限制他的思维",null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L,"刀的两侧是光明与黑暗","哲学,爱情",88,"用一把刀划分了爱恨"));
        books1.add(new Book(2L,"一个人不能死在同一把刀下","个人成长,爱情",99,"讲述如何从失败中明悟真理"));

        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(3L,"那风吹不到的地方","哲学",85,"带你用思维去领略世界的尽头"));
        books2.add(new Book(4L,"吹或不吹","爱情,个人传记",56,"一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L,"你的剑就是我的剑","爱情",56,"无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L,"风与剑","个人传记",100,"两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author,author2,author3,author4));
        return authorList;
    }
}
