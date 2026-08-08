package app.config;
import app.model.dto.item.ItemRequest;
import app.model.entity.item.Genre;
import app.model.entity.item.Medium;
import app.service.item.ItemService;

// THIS IS A TEST FILE
// In NerdyLogsAppApplication you can find a commented run command that will add these to database to allow easier testing with more units
// Items can still be added from the admin account, but if you want to test with more items, I thought this would make it easier

public class TestData {

    public static void load(ItemService itemService) {
        // GAME
        create(itemService, "Elden Ring", Medium.GAME, Genre.FANTASY, 2022, "Cover of Elden Ring.", "https://preview.redd.it/ultra-high-quality-elden-ring-poster-7350x10392-v0-ow18c13yni571.jpg?width=1225&format=pjpg&auto=webp&s=54724e78959b07681d3227926c5dd16eb7640eba");
        create(itemService, "Dark Souls III", Medium.GAME, Genre.FANTASY, 2016, "Cover of Dark Souls III.", "https://m.media-amazon.com/images/M/MV5BZDdmODgzODYtNzFiNy00MzRkLWE5MjQtZTdlOTIwOTNkNTBkXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Bloodborne", Medium.GAME, Genre.HORROR, 2015, "Cover of Bloodborne.", "https://image.api.playstation.com/vulcan/img/rnd/202010/2614/Sy5e8DmeKIJVjlAGraPAJYkT.png");
        create(itemService, "Sekiro: Shadows Die Twice", Medium.GAME, Genre.ACTION, 2019, "Cover of Sekiro: Shadows Die Twice.", "https://i.pinimg.com/1200x/15/8b/91/158b91977239f24388b5ca6fb7fcf4f4.jpg");
        create(itemService, "The Witcher 3: Wild Hunt", Medium.GAME, Genre.FANTASY, 2015, "Cover of The Witcher 3: Wild Hunt.", "https://image.api.playstation.com/vulcan/ap/rnd/202211/0711/qezXTVn1ExqBjVjR5Ipm97IK.png");
        create(itemService, "Cyberpunk 2077", Medium.GAME, Genre.ACTION, 2020, "Cover of Cyberpunk 2077.", "https://store-images.s-microsoft.com/image/apps.47379.63407868131364914.bcaa868c-407e-42c2-baeb-48a3c9f29b54.89bb995b-b066-4a53-9fe4-0260ce07e894");
        create(itemService, "Baldur's Gate 3", Medium.GAME, Genre.FANTASY, 2023, "Cover of Baldur's Gate 3.", "https://i.redd.it/9wmsaph9ddob1.jpg");
        create(itemService, "Red Dead Redemption 2", Medium.GAME, Genre.ACTION, 2018, "Cover of Red Dead Redemption 2.", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/e5d6f175-3a2e-493d-b7f2-5f70852b4f2e/dermu4d-3d22a435-f639-4fc5-864d-0c78ac450eff.jpg/v1/fill/w_683,h_1171,q_70,strp/red_dead_redemption_2_game_cover_by_love_myart_dermu4d-pre.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MjE5NSIsInBhdGgiOiIvZi9lNWQ2ZjE3NS0zYTJlLTQ5M2QtYjdmMi01ZjcwODUyYjRmMmUvZGVybXU0ZC0zZDIyYTQzNS1mNjM5LTRmYzUtODY0ZC0wYzc4YWM0NTBlZmYuanBnIiwid2lkdGgiOiI8PTEyODAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.IEFmIALxJhUthrr1pwdMdWUp-5Z-Hsc_XILLQcoBa8I");
        create(itemService, "God of War Ragnarök", Medium.GAME, Genre.ACTION, 2022, "Cover of God of War Ragnarök.", "https://i.pinimg.com/736x/f6/41/61/f6416126cfbb5e17b0d8a66897337e19.jpg");
        create(itemService, "Ghost of Tsushima", Medium.GAME, Genre.ACTION, 2020, "Cover of Ghost of Tsushima.", "https://4kwallpapers.com/images/wallpapers/ghost-of-tsushima-768x1024-12072.jpg");
        create(itemService, "Hollow Knight", Medium.GAME, Genre.FANTASY, 2017, "Cover of Hollow Knight.", "https://m.media-amazon.com/images/M/MV5BMGIyYmJmZDgtOWQ1Ny00NDFiLTk2OTgtM2Q2ZWQ4OWIxZjg3XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Hades", Medium.GAME, Genre.FANTASY, 2020, "Cover of Hades.", "https://m.media-amazon.com/images/I/71FjVhf-SlL.jpg");
        create(itemService, "Minecraft", Medium.GAME, Genre.OTHER, 2011, "Cover of Minecraft.", "https://upload.wikimedia.org/wikinews/en/7/7a/Minecraft_game_cover.jpeg?utm_source=en.wikinews.org&utm_campaign=index&utm_content=original");
        create(itemService, "Terraria", Medium.GAME, Genre.OTHER, 2011, "Cover of Terraria.", "https://cdn.mobygames.com/covers/6798565-terraria-xbox-one-front-cover.jpg");
        create(itemService, "Stardew Valley", Medium.GAME, Genre.OTHER, 2016, "Cover of Stardew Valley.", "https://m.media-amazon.com/images/I/613E9WbS7qL._UF1000,1000_QL80_.jpg");
        create(itemService, "Resident Evil 4 Remake", Medium.GAME, Genre.HORROR, 2023, "Cover of Resident Evil 4 Remake.", "https://images.g2a.com/1024x768/1x1x0/resident-evil-4-remake-pc-steam-key-global-i10000337236002/b9cb344fe08e481783ed97c3");
        create(itemService, "Persona 5 Royal", Medium.GAME, Genre.FANTASY, 2019, "Cover of Persona 5 Royal.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmeYDybR5TBx_vKbSIUBKN2BOFU_5PLRoZZ22YWzqspZdVvahGX3CFtuw&s=10");
        create(itemService, "The Legend of Zelda: Tears of the Kingdom", Medium.GAME, Genre.FANTASY, 2023, "Cover of The Legend of Zelda: Tears of the Kingdom.", "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/00036c1f-5e5c-4a3e-9cd8-a901da94fa48/dfp97il-aeaf20b6-83fa-4cb2-96f8-95e5889f8552.png/v1/fill/w_700,h_1142/the_legend_of_zelda_tears_of_the_kingdom_cover_art_by_edmaxxwtf_dfp97il-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTE0MiIsInBhdGgiOiIvZi8wMDAzNmMxZi01ZTVjLTRhM2UtOWNkOC1hOTAxZGE5NGZhNDgvZGZwOTdpbC1hZWFmMjBiNi04M2ZhLTRjYjItOTZmOC05NWU1ODg5Zjg1NTIucG5nIiwid2lkdGgiOiI8PTcwMCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.YCFYTnBKJvGtwGvxs20ChCftxNYWcqwri2DZh6waSGk");
        create(itemService, "Mass Effect Legendary Edition", Medium.GAME, Genre.ACTION, 2021, "Cover of Mass Effect Legendary Edition.", "https://i.redd.it/5u57f04xpbx61.png");
        create(itemService, "Disco Elysium", Medium.GAME, Genre.CRIME, 2019, "Cover of Disco Elysium.", "https://m.media-amazon.com/images/M/MV5BYzc5YmEwOTItMWI5Ni00YTM4LTk3NzktYWVmNWU4NzE4NDJmXkEyXkFqcGc@._V1_.jpg");

        // MOVIE
        create(itemService, "The Dark Knight", Medium.MOVIE, Genre.ACTION, 2008, "Test movie entry for The Dark Knight.", "https://placehold.co/300x450?text=The%20Dark%20Knight");
        create(itemService, "Inception", Medium.MOVIE, Genre.THRILLER, 2010, "Test movie entry for Inception.", "https://placehold.co/300x450?text=Inception");
        create(itemService, "Interstellar", Medium.MOVIE, Genre.OTHER, 2014, "Test movie entry for Interstellar.", "https://placehold.co/300x450?text=Interstellar");
        create(itemService, "The Lord of the Rings: The Return of the King", Medium.MOVIE, Genre.FANTASY, 2003, "Test movie entry for The Lord of the Rings: The Return of the King.", "https://placehold.co/300x450?text=The%20Lord%20of%20the%20Rings%3A%20The%20Return%20of%20the%20King");
        create(itemService, "The Matrix", Medium.MOVIE, Genre.ACTION, 1999, "Test movie entry for The Matrix.", "https://placehold.co/300x450?text=The%20Matrix");
        create(itemService, "Fight Club", Medium.MOVIE, Genre.THRILLER, 1999, "Test movie entry for Fight Club.", "https://placehold.co/300x450?text=Fight%20Club");
        create(itemService, "Whiplash", Medium.MOVIE, Genre.OTHER, 2014, "Test movie entry for Whiplash.", "https://placehold.co/300x450?text=Whiplash");
        create(itemService, "Parasite", Medium.MOVIE, Genre.THRILLER, 2019, "Test movie entry for Parasite.", "https://placehold.co/300x450?text=Parasite");
        create(itemService, "Dune", Medium.MOVIE, Genre.FANTASY, 2021, "Test movie entry for Dune.", "https://placehold.co/300x450?text=Dune");
        create(itemService, "Oppenheimer", Medium.MOVIE, Genre.OTHER, 2023, "Test movie entry for Oppenheimer.", "https://placehold.co/300x450?text=Oppenheimer");
        create(itemService, "Everything Everywhere All at Once", Medium.MOVIE, Genre.COMEDY, 2022, "Test movie entry for Everything Everywhere All at Once.", "https://placehold.co/300x450?text=Everything%20Everywhere%20All%20at%20Once");
        create(itemService, "The Prestige", Medium.MOVIE, Genre.THRILLER, 2006, "Test movie entry for The Prestige.", "https://placehold.co/300x450?text=The%20Prestige");
        create(itemService, "Blade Runner 2049", Medium.MOVIE, Genre.OTHER, 2017, "Test movie entry for Blade Runner 2049.", "https://placehold.co/300x450?text=Blade%20Runner%202049");
        create(itemService, "The Batman", Medium.MOVIE, Genre.CRIME, 2022, "Test movie entry for The Batman.", "https://placehold.co/300x450?text=The%20Batman");
        create(itemService, "Spider-Man: Into the Spider-Verse", Medium.MOVIE, Genre.ACTION, 2018, "Test movie entry for Spider-Man: Into the Spider-Verse.", "https://placehold.co/300x450?text=Spider-Man%3A%20Into%20the%20Spider-Verse");
        create(itemService, "John Wick", Medium.MOVIE, Genre.ACTION, 2014, "Test movie entry for John Wick.", "https://placehold.co/300x450?text=John%20Wick");
        create(itemService, "The Shawshank Redemption", Medium.MOVIE, Genre.OTHER, 1994, "Test movie entry for The Shawshank Redemption.", "https://placehold.co/300x450?text=The%20Shawshank%20Redemption");
        create(itemService, "Gladiator", Medium.MOVIE, Genre.ACTION, 2000, "Test movie entry for Gladiator.", "https://placehold.co/300x450?text=Gladiator");
        create(itemService, "The Green Mile", Medium.MOVIE, Genre.FANTASY, 1999, "Test movie entry for The Green Mile.", "https://placehold.co/300x450?text=The%20Green%20Mile");
        create(itemService, "Pulp Fiction", Medium.MOVIE, Genre.CRIME, 1994, "Test movie entry for Pulp Fiction.", "https://placehold.co/300x450?text=Pulp%20Fiction");

        // SERIES
        create(itemService, "Breaking Bad", Medium.SERIES, Genre.CRIME, 2008, "Test series entry for Breaking Bad.", "https://placehold.co/300x450?text=Breaking%20Bad");
        create(itemService, "Better Call Saul", Medium.SERIES, Genre.CRIME, 2015, "Test series entry for Better Call Saul.", "https://placehold.co/300x450?text=Better%20Call%20Saul");
        create(itemService, "Dark", Medium.SERIES, Genre.THRILLER, 2017, "Test series entry for Dark.", "https://placehold.co/300x450?text=Dark");
        create(itemService, "Game of Thrones", Medium.SERIES, Genre.FANTASY, 2011, "Test series entry for Game of Thrones.", "https://placehold.co/300x450?text=Game%20of%20Thrones");
        create(itemService, "House of the Dragon", Medium.SERIES, Genre.FANTASY, 2022, "Test series entry for House of the Dragon.", "https://placehold.co/300x450?text=House%20of%20the%20Dragon");
        create(itemService, "The Last of Us", Medium.SERIES, Genre.HORROR, 2023, "Test series entry for The Last of Us.", "https://placehold.co/300x450?text=The%20Last%20of%20Us");
        create(itemService, "The Office", Medium.SERIES, Genre.COMEDY, 2005, "Test series entry for The Office.", "https://placehold.co/300x450?text=The%20Office");
        create(itemService, "Stranger Things", Medium.SERIES, Genre.HORROR, 2016, "Test series entry for Stranger Things.", "https://placehold.co/300x450?text=Stranger%20Things");
        create(itemService, "Sherlock", Medium.SERIES, Genre.CRIME, 2010, "Test series entry for Sherlock.", "https://placehold.co/300x450?text=Sherlock");
        create(itemService, "Arcane", Medium.SERIES, Genre.FANTASY, 2021, "Test series entry for Arcane.", "https://placehold.co/300x450?text=Arcane");
        create(itemService, "The Boys", Medium.SERIES, Genre.ACTION, 2019, "Test series entry for The Boys.", "https://placehold.co/300x450?text=The%20Boys");
        create(itemService, "Severance", Medium.SERIES, Genre.THRILLER, 2022, "Test series entry for Severance.", "https://placehold.co/300x450?text=Severance");
        create(itemService, "The Mandalorian", Medium.SERIES, Genre.ACTION, 2019, "Test series entry for The Mandalorian.", "https://placehold.co/300x450?text=The%20Mandalorian");
        create(itemService, "Peaky Blinders", Medium.SERIES, Genre.CRIME, 2013, "Test series entry for Peaky Blinders.", "https://placehold.co/300x450?text=Peaky%20Blinders");
        create(itemService, "Mr. Robot", Medium.SERIES, Genre.THRILLER, 2015, "Test series entry for Mr. Robot.", "https://placehold.co/300x450?text=Mr.%20Robot");
        create(itemService, "The Bear", Medium.SERIES, Genre.COMEDY, 2022, "Test series entry for The Bear.", "https://placehold.co/300x450?text=The%20Bear");
        create(itemService, "Chernobyl", Medium.SERIES, Genre.THRILLER, 2019, "Test series entry for Chernobyl.", "https://placehold.co/300x450?text=Chernobyl");
        create(itemService, "True Detective", Medium.SERIES, Genre.CRIME, 2014, "Test series entry for True Detective.", "https://placehold.co/300x450?text=True%20Detective");
        create(itemService, "Black Mirror", Medium.SERIES, Genre.THRILLER, 2011, "Test series entry for Black Mirror.", "https://placehold.co/300x450?text=Black%20Mirror");
        create(itemService, "Wednesday", Medium.SERIES, Genre.COMEDY, 2022, "Test series entry for Wednesday.", "https://placehold.co/300x450?text=Wednesday");

        // ANIME
        create(itemService, "Attack on Titan", Medium.ANIME, Genre.ACTION, 2013, "Test anime entry for Attack on Titan.", "https://placehold.co/300x450?text=Attack%20on%20Titan");
        create(itemService, "Death Note", Medium.ANIME, Genre.THRILLER, 2006, "Test anime entry for Death Note.", "https://placehold.co/300x450?text=Death%20Note");
        create(itemService, "Fullmetal Alchemist: Brotherhood", Medium.ANIME, Genre.FANTASY, 2009, "Test anime entry for Fullmetal Alchemist: Brotherhood.", "https://placehold.co/300x450?text=Fullmetal%20Alchemist%3A%20Brotherhood");
        create(itemService, "Demon Slayer", Medium.ANIME, Genre.ACTION, 2019, "Test anime entry for Demon Slayer.", "https://placehold.co/300x450?text=Demon%20Slayer");
        create(itemService, "Jujutsu Kaisen", Medium.ANIME, Genre.ACTION, 2020, "Test anime entry for Jujutsu Kaisen.", "https://placehold.co/300x450?text=Jujutsu%20Kaisen");
        create(itemService, "Chainsaw Man", Medium.ANIME, Genre.HORROR, 2022, "Test anime entry for Chainsaw Man.", "https://placehold.co/300x450?text=Chainsaw%20Man");
        create(itemService, "Steins;Gate", Medium.ANIME, Genre.THRILLER, 2011, "Test anime entry for Steins;Gate.", "https://placehold.co/300x450?text=Steins%3BGate");
        create(itemService, "Hunter x Hunter", Medium.ANIME, Genre.ACTION, 2011, "Test anime entry for Hunter x Hunter.", "https://placehold.co/300x450?text=Hunter%20x%20Hunter");
        create(itemService, "Vinland Saga", Medium.ANIME, Genre.ACTION, 2019, "Test anime entry for Vinland Saga.", "https://placehold.co/300x450?text=Vinland%20Saga");
        create(itemService, "Frieren: Beyond Journey's End", Medium.ANIME, Genre.FANTASY, 2023, "Test anime entry for Frieren: Beyond Journey's End.", "https://placehold.co/300x450?text=Frieren%3A%20Beyond%20Journeys%20End");
        create(itemService, "Mob Psycho 100", Medium.ANIME, Genre.COMEDY, 2016, "Test anime entry for Mob Psycho 100.", "https://placehold.co/300x450?text=Mob%20Psycho%20100");
        create(itemService, "One Punch Man", Medium.ANIME, Genre.COMEDY, 2015, "Test anime entry for One Punch Man.", "https://placehold.co/300x450?text=One%20Punch%20Man");
        create(itemService, "Your Lie in April", Medium.ANIME, Genre.ROMANCE, 2014, "Test anime entry for Your Lie in April.", "https://placehold.co/300x450?text=Your%20Lie%20in%20April");
        create(itemService, "Haikyuu!!", Medium.ANIME, Genre.COMEDY, 2014, "Test anime entry for Haikyuu!!.", "https://placehold.co/300x450?text=Haikyuu%21%21");
        create(itemService, "Spy x Family", Medium.ANIME, Genre.COMEDY, 2022, "Test anime entry for Spy x Family.", "https://placehold.co/300x450?text=Spy%20x%20Family");
        create(itemService, "Code Geass", Medium.ANIME, Genre.ACTION, 2006, "Test anime entry for Code Geass.", "https://placehold.co/300x450?text=Code%20Geass");
        create(itemService, "Cowboy Bebop", Medium.ANIME, Genre.ACTION, 1998, "Test anime entry for Cowboy Bebop.", "https://placehold.co/300x450?text=Cowboy%20Bebop");
        create(itemService, "Monster", Medium.ANIME, Genre.THRILLER, 2004, "Test anime entry for Monster.", "https://placehold.co/300x450?text=Monster");
        create(itemService, "Neon Genesis Evangelion", Medium.ANIME, Genre.ACTION, 1995, "Test anime entry for Neon Genesis Evangelion.", "https://placehold.co/300x450?text=Neon%20Genesis%20Evangelion");
        create(itemService, "Made in Abyss", Medium.ANIME, Genre.FANTASY, 2017, "Test anime entry for Made in Abyss.", "https://placehold.co/300x450?text=Made%20in%20Abyss");

        // BOOK
        create(itemService, "The Hobbit", Medium.BOOK, Genre.FANTASY, 1937, "Test book entry for The Hobbit.", "https://placehold.co/300x450?text=The%20Hobbit");
        create(itemService, "The Fellowship of the Ring", Medium.BOOK, Genre.FANTASY, 1954, "Test book entry for The Fellowship of the Ring.", "https://placehold.co/300x450?text=The%20Fellowship%20of%20the%20Ring");
        create(itemService, "The Two Towers", Medium.BOOK, Genre.FANTASY, 1954, "Test book entry for The Two Towers.", "https://placehold.co/300x450?text=The%20Two%20Towers");
        create(itemService, "The Return of the King", Medium.BOOK, Genre.FANTASY, 1955, "Test book entry for The Return of the King.", "https://placehold.co/300x450?text=The%20Return%20of%20the%20King");
        create(itemService, "Mistborn: The Final Empire", Medium.BOOK, Genre.FANTASY, 2006, "Test book entry for Mistborn: The Final Empire.", "https://placehold.co/300x450?text=Mistborn%3A%20The%20Final%20Empire");
        create(itemService, "The Well of Ascension", Medium.BOOK, Genre.FANTASY, 2007, "Test book entry for The Well of Ascension.", "https://placehold.co/300x450?text=The%20Well%20of%20Ascension");
        create(itemService, "The Hero of Ages", Medium.BOOK, Genre.FANTASY, 2008, "Test book entry for The Hero of Ages.", "https://placehold.co/300x450?text=The%20Hero%20of%20Ages");
        create(itemService, "The Name of the Wind", Medium.BOOK, Genre.FANTASY, 2007, "Test book entry for The Name of the Wind.", "https://placehold.co/300x450?text=The%20Name%20of%20the%20Wind");
        create(itemService, "The Wise Man's Fear", Medium.BOOK, Genre.FANTASY, 2011, "Test book entry for The Wise Man's Fear.", "https://placehold.co/300x450?text=The%20Wise%20Mans%20Fear");
        create(itemService, "1984", Medium.BOOK, Genre.OTHER, 1949, "Test book entry for 1984.", "https://placehold.co/300x450?text=1984");
        create(itemService, "Animal Farm", Medium.BOOK, Genre.OTHER, 1945, "Test book entry for Animal Farm.", "https://placehold.co/300x450?text=Animal%20Farm");
        create(itemService, "Dune", Medium.BOOK, Genre.FANTASY, 1965, "Test book entry for Dune.", "https://placehold.co/300x450?text=Dune");
        create(itemService, "The Catcher in the Rye", Medium.BOOK, Genre.OTHER, 1951, "Test book entry for The Catcher in the Rye.", "https://placehold.co/300x450?text=The%20Catcher%20in%20the%20Rye");
        create(itemService, "To Kill a Mockingbird", Medium.BOOK, Genre.CRIME, 1960, "Test book entry for To Kill a Mockingbird.", "https://placehold.co/300x450?text=To%20Kill%20a%20Mockingbird");
        create(itemService, "The Silent Patient", Medium.BOOK, Genre.THRILLER, 2019, "Test book entry for The Silent Patient.", "https://placehold.co/300x450?text=The%20Silent%20Patient");
        create(itemService, "Project Hail Mary", Medium.BOOK, Genre.OTHER, 2021, "Test book entry for Project Hail Mary.", "https://placehold.co/300x450?text=Project%20Hail%20Mary");
        create(itemService, "The Martian", Medium.BOOK, Genre.OTHER, 2011, "Test book entry for The Martian.", "https://placehold.co/300x450?text=The%20Martian");
        create(itemService, "The Way of Kings", Medium.BOOK, Genre.FANTASY, 2010, "Test book entry for The Way of Kings.", "https://placehold.co/300x450?text=The%20Way%20of%20Kings");
        create(itemService, "Words of Radiance", Medium.BOOK, Genre.FANTASY, 2014, "Test book entry for Words of Radiance.", "https://placehold.co/300x450?text=Words%20of%20Radiance");
        create(itemService, "The Shining", Medium.BOOK, Genre.HORROR, 1977, "Test book entry for The Shining.", "https://placehold.co/300x450?text=The%20Shining");

    }

    private static void create(ItemService itemService, String name, Medium medium, Genre genre, int releaseYear, String description, String pictureCover) {
        try {
            itemService.createItem(ItemRequest.builder()
                    .itemName(name)
                    .medium(medium)
                    .genre(genre)
                    .releaseYear(releaseYear)
                    .description(description)
                    .pictureCover(pictureCover)
                    .build());
        } catch (IllegalArgumentException ignored) {
            // Ignore duplicates, so you can run this more than once.
        }
    }
}
