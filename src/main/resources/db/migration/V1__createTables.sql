create table article
(
    article_id int auto_increment
        primary key
);

create table article_params
(
    article_id int not null
        primary key,
    language text not null,
    max_ngram_size decimal not null,
    deduplication_thresold decimal not null,
    deduplication_algo text not null,
    window_size decimal not null,
    number_of_keywords int not null,
    text text not null
);

create table article_scores_yake
(
    article_id int not null
        primary key,
    ngram varchar(45) not null,
    score float not null
);

create table if not exists classes
(
    class_id int auto_increment
        primary key,
    keyword_id int not null,
    class_weight decimal not null,
    class_name varchar(45) null
);

create table if not exists keywords
(
    keyword_id int auto_increment
        primary key,
    keyword_text varchar(45) not null
);