DROP DATABASE IF EXISTS `comp3095`;
CREATE SCHEMA `comp3095` ;
GRANT ALL ON COMP3095.* TO 'admin'@'localhost' IDENTIFIED BY 'admin';
USE COMP3095;

CREATE TABLE `comp3095`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NULL,
  `phone` VARCHAR(45) NULL,
  `year` VARCHAR(45) NULL,
  `major` VARCHAR(45) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  INSERT INTO `comp3095`.`users`(firstname,lastname,email,phone,year,major,username,password)
  VALUES('Michael','Jimma','Michael.Jimma@georgebrown.ca','9874563210','2012','Computer Science','100963147','password'),
  ('Ramzi','Haddad','Ramzi.Haddad@georgebrown.ca','125478952','2013','Computer Science','100885168','password'),
  ('Daniel','Dos Santos','Daniel.Dosantos@georgebrown.ca','1234567890','2014','Computer Science','100706404','password'),
  ('Rany','Akad','Rany.Akad@georgebrown.ca','4581269854','2012','Computer Science','100821528','password'),
  ('Felix','Chan','Felix.Chan@georgebrown.ca','458789250','2015','Computer Science','100991675','password');

 CREATE TABLE `comp3095`.`blogs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `created_on` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `blogs_user_id_idx` (`user_id` ASC),
  CONSTRAINT `blogs_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `comp3095`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT INTO `comp3095`.`blogs`(user_id,content,created_on)
VALUE(2,'Java is neither a good nor a bad language. It is a mediocre language, and there is no struggle.',CURRENT_DATE),
(3,'Why do some people hate C#?',CURRENT_DATE),
(4,'Visual Basic seems clumsy.',CURRENT_DATE),
(5,' C++ is by far my favourite programming language',CURRENT_DATE),
(1,'I''m so exciting about the new ASP.NET Core!',CURRENT_DATE);

CREATE TABLE `comp3095`.`comments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `blog_id` INT NOT NULL,
  `content` VARCHAR(500) NOT NULL,
  `created_on` DATE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `comment_user_id_idx` (`user_id` ASC),
  INDEX `comment_blog_id_idx` (`blog_id` ASC),
  CONSTRAINT `comment_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `comp3095`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `comment_blog_id`
    FOREIGN KEY (`blog_id`)
    REFERENCES `comp3095`.`blogs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
INSERT INTO `comp3095`.`comments`(user_id,blog_id,content,created_on)
VALUE(2,5,'If you are looking for raw performance, ASP.NET Core would be my choice.','2016-12-01'),
(3,5,' The .NET Core 1.0 CLI is very new. Not only that, but .NET Core isn''t as complete as the full .NET Framework 4.6.','2016-12-06'),
(4,5,'ASP.NET Core 1.0 is not a continuation of ASP.NET 4.6. It is a whole new framework, a side-by-side project which happily lives alongside everything else we know.','2016-12-07'),
(5,5,'ASP.NET so it closely resembles and mimics Node.js.','2016-12-14');

  