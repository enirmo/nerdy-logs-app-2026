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
        create(itemService, "The Dark Knight", Medium.MOVIE, Genre.ACTION, 2008, "Cover of The Dark Knight.", "https://play-lh.googleusercontent.com/qhfncXOqccJ5Y_IBPaRy0O79QZQDl7L5FyKQAsLFICt8c9-2Vfmqd2bniAPESto0ZmSLTOzjl-o1F_jgb2Nr");
        create(itemService, "Inception", Medium.MOVIE, Genre.THRILLER, 2010, "Cover of Inception.", "https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg");
        create(itemService, "Interstellar", Medium.MOVIE, Genre.OTHER, 2014, "Cover of Interstellar.", "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "The Lord of the Rings: The Return of the King", Medium.MOVIE, Genre.FANTASY, 2003, "Cover of The Lord of the Rings: The Return of the King.", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p33156_p_v8_aq.jpg");
        create(itemService, "The Matrix", Medium.MOVIE, Genre.ACTION, 1999, "Cover of The Matrix.", "https://www.cinematerial.com/p/500x/3loqorvq/the-matrix-movie-cover.jpg?v=1456805952");
        create(itemService, "Fight Club", Medium.MOVIE, Genre.THRILLER, 1999, "Cover of Fight Club.", "https://m.media-amazon.com/images/M/MV5BOTgyOGQ1NDItNGU3Ny00MjU3LTg2YWEtNmEyYjBiMjI1Y2M5XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Whiplash", Medium.MOVIE, Genre.OTHER, 2014, "Cover of Whiplash.", "https://m.media-amazon.com/images/M/MV5BMTA2ZWFkZWQtZmNjYy00ZjhhLTk5MjEtMWIwNzEzMTgxZDk5XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Parasite", Medium.MOVIE, Genre.THRILLER, 2019, "Cover of Parasite.", "https://m.media-amazon.com/images/M/MV5BYjk1Y2U4MjQtY2ZiNS00OWQyLWI3MmYtZWUwNmRjYWRiNWNhXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Dune", Medium.MOVIE, Genre.FANTASY, 2021, "Cover of Dune.", "https://m.media-amazon.com/images/M/MV5BNTc0YmQxMjEtODI5MC00NjFiLTlkMWUtOGQ5NjFmYWUyZGJhXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Oppenheimer", Medium.MOVIE, Genre.OTHER, 2023, "Cover of Oppenheimer.", "https://www.hollywoodreporter.com/wp-content/uploads/2022/07/Oppenheimer-Movie-Poster-Universal-Publicity-EMBED-2022-.jpg?w=1000");
        create(itemService, "Everything Everywhere All at Once", Medium.MOVIE, Genre.COMEDY, 2022, "Cover of Everything Everywhere All at Once.", "https://static.wikia.nocookie.net/greatestmovies/images/3/30/Cu_c_chi_n_a_v_tr_-_payoff_poster_-_k_ch_th_c_fb_-_dkkc_24062022_1_.jpg/revision/latest?cb=20230826013231");
        create(itemService, "The Prestige", Medium.MOVIE, Genre.THRILLER, 2006, "Cover of The Prestige.", "https://image.tmdb.org/t/p/original/Ag2B2KHKQPukjH7WutmgnnSNurZ.jpg");
        create(itemService, "Blade Runner 2049", Medium.MOVIE, Genre.OTHER, 2017, "Cover of Blade Runner 2049.", "https://m.media-amazon.com/images/M/MV5BNzA1Njg4NzYxOV5BMl5BanBnXkFtZTgwODk5NjU3MzI@._V1_.jpg");
        create(itemService, "The Batman", Medium.MOVIE, Genre.CRIME, 2022, "Cover of The Batman.", "https://m.media-amazon.com/images/M/MV5BMmU5NGJlMzAtMGNmOC00YjJjLTgyMzUtNjAyYmE4Njg5YWMyXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Spider-Man: Into the Spider-Verse", Medium.MOVIE, Genre.ACTION, 2018, "Cover of Spider-Man: Into the Spider-Verse.", "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_.jpg");
        create(itemService, "John Wick", Medium.MOVIE, Genre.ACTION, 2014, "Cover of John Wick.", "https://m.media-amazon.com/images/M/MV5BMTU2NjA1ODgzMF5BMl5BanBnXkFtZTgwMTM2MTI4MjE@._V1_.jpg");
        create(itemService, "The Shawshank Redemption", Medium.MOVIE, Genre.OTHER, 1994, "Cover of The Shawshank Redemption.", "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Gladiator", Medium.MOVIE, Genre.ACTION, 2000, "Cover of Gladiator.", "https://m.media-amazon.com/images/M/MV5BYWQ4YmNjYjEtOWE1Zi00Y2U4LWI4NTAtMTU0MjkxNWQ1ZmJiXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "The Green Mile", Medium.MOVIE, Genre.FANTASY, 1999, "Cover of The Green Mile.", "https://m.media-amazon.com/images/I/71+gLki+J8L._AC_UF894,1000_QL80_.jpg");
        create(itemService, "Pulp Fiction", Medium.MOVIE, Genre.CRIME, 1994, "Cover of Pulp Fiction.", "https://m.media-amazon.com/images/M/MV5BYTViYTE3ZGQtNDBlMC00ZTAyLTkyODMtZGRiZDg0MjA2YThkXkEyXkFqcGc@._V1_.jpg");

        // SERIES
        create(itemService, "Breaking Bad", Medium.SERIES, Genre.CRIME, 2008, "Cover of Breaking Bad.", "https://image.tmdb.org/t/p/w500/e3oGYpoTUhOFK0BJfloru5ZmGV.jpg");
        create(itemService, "Better Call Saul", Medium.SERIES, Genre.CRIME, 2015, "Cover of Better Call Saul.", "https://resizing.flixster.com/4qvRGkhvlxWDI_w_DjfuNzqwBT0=/ems.cHJkLWVtcy1hc3NldHMvdHZzZWFzb24vUlRUVjYzOTQ0OS53ZWJw");
        create(itemService, "Dark", Medium.SERIES, Genre.THRILLER, 2017, "Cover of Dark.", "https://resizing.flixster.com/lpJkDxnEFNQT1OWJjnmYfvpAHJ0=/ems.cHJkLWVtcy1hc3NldHMvdHZzZXJpZXMvUlRUVjI2NjgyOS53ZWJw");
        create(itemService, "Game of Thrones", Medium.SERIES, Genre.FANTASY, 2011, "Cover of Game of Thrones.", "https://m.media-amazon.com/images/M/MV5BNGYxOGJkMjItZjVkZC00OGEzLWExNjktOTZmNGZhZmRlMTk2XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "House of the Dragon", Medium.SERIES, Genre.FANTASY, 2022, "Cover of House of the Dragon.", "https://assets.lareviewofbooks.org/uploads/202210House-of-the-Dragon.jpg");
        create(itemService, "The Last of Us", Medium.SERIES, Genre.HORROR, 2023, "Cover of The Last of Us.", "https://m.media-amazon.com/images/M/MV5BYWI3ODJlMzktY2U5NC00ZjdlLWE1MGItNWQxZDk3NWNjN2RhXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "The Office", Medium.SERIES, Genre.COMEDY, 2005, "Cover of The Office.", "https://m.media-amazon.com/images/M/MV5BZjQwYzBlYzUtZjhhOS00ZDQ0LWE0NzAtYTk4MjgzZTNkZWEzXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Stranger Things", Medium.SERIES, Genre.HORROR, 2016, "Cover of Stranger Things.", "https://m.media-amazon.com/images/M/MV5BOWU2NjY5NWQtMjdkZi00ODJlLThkZTAtMzFlYmJmMGE2NjZkXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Sherlock", Medium.SERIES, Genre.CRIME, 2010, "Cover of Sherlock.", "https://cdn11.bigcommerce.com/s-wq5l7bzi/images/stencil/1280x1280/products/8879/20256/61dbY2p5P-L._SL1200___78446.1721620649.jpg?c=2");
        create(itemService, "Arcane", Medium.SERIES, Genre.FANTASY, 2021, "Cover of Arcane.", "https://m.media-amazon.com/images/I/61gRrypbnJL._AC_UF894,1000_QL80_.jpg");
        create(itemService, "The Boys", Medium.SERIES, Genre.ACTION, 2019, "Cover of The Boys.", "https://resizing.flixster.com/mmvBo8CgJiLxvYSimsMguhNEv58=/ems.cHJkLWVtcy1hc3NldHMvdHZzZWFzb24vYWM4MWJjZjUtN2I0My00NmQwLThmZjEtOTA0NTU4OWNlOWE3LmpwZw==");
        create(itemService, "Severance", Medium.SERIES, Genre.THRILLER, 2022, "Cover of Severance.", "https://m.media-amazon.com/images/M/MV5BZDI5YzJhODQtMzQyNy00YWNmLWIxMjUtNDBjNjA5YWRjMzExXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "The Mandalorian", Medium.SERIES, Genre.ACTION, 2019, "Cover of The Mandalorian.", "https://m.media-amazon.com/images/I/81oP0pVLi5L._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "Peaky Blinders", Medium.SERIES, Genre.CRIME, 2013, "Cover of Peaky Blinders.", "https://mir-s3-cdn-cf.behance.net/project_modules/1400/d7590f135861167.61ef6fb49f8e4.jpg");
        create(itemService, "Mr. Robot", Medium.SERIES, Genre.THRILLER, 2015, "Cover of Mr. Robot.", "https://m.media-amazon.com/images/M/MV5BOTg4NTBiZDAtZTc0YS00NzZlLTg4Y2ItNGQ3M2ZlMDM5MWQzXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "The Bear", Medium.SERIES, Genre.COMEDY, 2022, "Cover of The Bear.", "https://shop.fxnetworks.com/cdn/shop/products/TB-S1KA-47-100123-RO_1080x.png?v=1663340447");
        create(itemService, "Chernobyl", Medium.SERIES, Genre.THRILLER, 2019, "Cover of Chernobyl.", "https://m.media-amazon.com/images/I/81R-QNgAjRL._AC_UF894,1000_QL80_.jpg");
        create(itemService, "True Detective", Medium.SERIES, Genre.CRIME, 2014, "Cover of True Detective.", "https://m.media-amazon.com/images/M/MV5BYjgwYzA1NWMtNDYyZi00ZGQyLWI5NTktMDYwZjE2OTIwZWEwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Black Mirror", Medium.SERIES, Genre.THRILLER, 2011, "Cover of Black Mirror.", "https://m.media-amazon.com/images/M/MV5BODcxMWI2NDMtYTc3NC00OTZjLWFmNmUtM2NmY2I1ODkxYzczXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Wednesday", Medium.SERIES, Genre.COMEDY, 2022, "Cover of Wednesday.", "https://m.media-amazon.com/images/M/MV5BY2E1NDI5OWEtODJmYi00Nzg2LWI4MjUtODFiMTU2YWViOTU3XkEyXkFqcGc@._V1_.jpg");

        // ANIME
        create(itemService, "Attack on Titan", Medium.ANIME, Genre.ACTION, 2013, "Cover of Attack on Titan.", "https://static.wikia.nocookie.net/shingekinokyojin/images/d/d8/Attack_on_Titan_Season_1.jpg/revision/latest/scale-to-width-down/1200?cb=20211005182832");
        create(itemService, "Death Note", Medium.ANIME, Genre.THRILLER, 2006, "Cover of Death Note.", "https://static.wikia.nocookie.net/deathnote/images/7/76/DEATH_NOTE_anime.jpg/revision/latest?cb=20170720215429");
        create(itemService, "Fullmetal Alchemist: Brotherhood", Medium.ANIME, Genre.FANTASY, 2009, "Cover of Fullmetal Alchemist: Brotherhood.", "https://m.media-amazon.com/images/M/MV5BMzNiODA5NjYtYWExZS00OTc4LTg3N2ItYWYwYTUyYmM5MWViXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Demon Slayer", Medium.ANIME, Genre.ACTION, 2019, "Cover of Demon Slayer.", "https://m.media-amazon.com/images/M/MV5BMWU1OGEwNmQtNGM3MS00YTYyLThmYmMtN2FjYzQzNzNmNTE0XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Jujutsu Kaisen", Medium.ANIME, Genre.ACTION, 2020, "Cover of Jujutsu Kaisen.", "https://m.media-amazon.com/images/M/MV5BMjBlNTExMDAtMWZjZi00MDc5LWFkMjgtZDU0ZWQ5ODk3YWY5XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Chainsaw Man", Medium.ANIME, Genre.HORROR, 2022, "Cover of Chainsaw Man.", "https://m.media-amazon.com/images/M/MV5BZGY2ZTM2MWMtNzA2OS00ZjJlLWIwZTMtMDBhN2EwYjZjZjEyXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Steins;Gate", Medium.ANIME, Genre.THRILLER, 2011, "Cover of Steins;Gate.", "https://m.media-amazon.com/images/M/MV5BZjI1YjZiMDUtZTI3MC00YTA5LWIzMmMtZmQ0NTZiYWM4NTYwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Hunter x Hunter", Medium.ANIME, Genre.ACTION, 2011, "Cover of Hunter x Hunter.", "https://m.media-amazon.com/images/M/MV5BNDBiMzMyMWItNDFkYy00MjBmLTk2ZDAtYmE2N2U4YzFlZDRmXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Vinland Saga", Medium.ANIME, Genre.ACTION, 2019, "Cover of Vinland Saga.", "https://resizing.flixster.com/-XZAfHZM39UwaGJIFWKAE8fS0ak=/v3/t/assets/p17127060_b_v8_aa.jpg");
        create(itemService, "Frieren: Beyond Journey's End", Medium.ANIME, Genre.FANTASY, 2023, "Cover of Frieren: Beyond Journey's End.", "https://m.media-amazon.com/images/M/MV5BZTI4ZGMxN2UtODlkYS00MTBjLWE1YzctYzc3NDViMGI0ZmJmXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Mob Psycho 100", Medium.ANIME, Genre.COMEDY, 2016, "Cover of Mob Psycho 100.", "https://m.media-amazon.com/images/M/MV5BYzU3NDM4ZjgtY2UyMi00YTczLTgyNDEtMjBiMDJlOGUxNjcxXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "One Punch Man", Medium.ANIME, Genre.COMEDY, 2015, "Cover of One Punch Man.", "https://m.media-amazon.com/images/M/MV5BNzMwOGQ5MWItNzE3My00ZDYyLTk4NzAtZWIyYWI0NTZhYzY0XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Your Lie in April", Medium.ANIME, Genre.ROMANCE, 2014, "Cover of Your Lie in April.", "https://m.media-amazon.com/images/M/MV5BZGMyYmFmNzgtMWQ4NS00MWE2LTg4YmEtZGY1MTBiODE0YmE5XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Haikyuu!!", Medium.ANIME, Genre.COMEDY, 2014, "Cover of Haikyuu!!.", "https://m.media-amazon.com/images/M/MV5BYjYxMWFlYTAtYTk0YS00NTMxLWJjNTQtM2E0NjdhYTRhNzE4XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Spy x Family", Medium.ANIME, Genre.COMEDY, 2022, "Cover of Spy x Family.", "https://d28hgpri8am2if.cloudfront.net/book_images/onix/cvr9781974743339/spy-x-family-the-official-anime-guide-mission-report-220409-0625-9781974743339_hr.jpg");
        create(itemService, "Code Geass", Medium.ANIME, Genre.ACTION, 2006, "Cover of Code Geass.", "https://m.media-amazon.com/images/M/MV5BMjA3OTUzNTIzOV5BMl5BanBnXkFtZTgwOTg4MTI1NjE@._V1_.jpg");
        create(itemService, "Cowboy Bebop", Medium.ANIME, Genre.ACTION, 1998, "Cover of Cowboy Bebop.", "https://m.media-amazon.com/images/M/MV5BM2VhZjk2MWMtZjc2OC00YzA4LWI0NzAtZGQ1YjVkOTk5YzVlXkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Monster", Medium.ANIME, Genre.THRILLER, 2004, "Cover of Monster.", "https://m.media-amazon.com/images/M/MV5BYzU2MWQ5NGQtYmNlMC00ZjJkLWJmODItZDM5MDM3YmUyMWJkXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg");
        create(itemService, "Neon Genesis Evangelion", Medium.ANIME, Genre.ACTION, 1995, "Cover of Neon Genesis Evangelion.", "https://m.media-amazon.com/images/M/MV5BYzljMDQxNmMtMGQyYS00ZDFkLWEyNzktNmQ0OTYzZWJkZTg2XkEyXkFqcGc@._V1_.jpg");
        create(itemService, "Made in Abyss", Medium.ANIME, Genre.FANTASY, 2017, "Cover of Made in Abyss.", "https://waltscomicshop.com/cdn/shop/files/made-in-abyss-vol-14-pre-order-7513257.jpg?v=1767350175");

        // BOOK
        create(itemService, "The Hobbit", Medium.BOOK, Genre.FANTASY, 1937, "Cover of The Hobbit.", "https://covers.shakespeareandcompany.com/97800074/9780007458424.jpg");
        create(itemService, "The Fellowship of the Ring", Medium.BOOK, Genre.FANTASY, 1954, "Cover of The Fellowship of the Ring.", "https://m.media-amazon.com/images/I/71eWusxmuKL.jpg");
        create(itemService, "The Two Towers", Medium.BOOK, Genre.FANTASY, 1954, "Cover of The Two Towers.", "https://m.media-amazon.com/images/I/916EvCstJfL._AC_UF894,1000_QL80_.jpg");
        create(itemService, "The Return of the King", Medium.BOOK, Genre.FANTASY, 1955, "Cover of The Return of the King.", "https://m.media-amazon.com/images/I/61I+emCM3eL._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "Mistborn: The Final Empire", Medium.BOOK, Genre.FANTASY, 2006, "Cover of Mistborn: The Final Empire.", "https://www.ciela.com/media/catalog/product/cache/9a7ceae8a5abbd0253425b80f9ef99a5/t/h/the_final_empire_1.jpg");
        create(itemService, "The Well of Ascension", Medium.BOOK, Genre.FANTASY, 2007, "Cover of The Well of Ascension.", "https://m.media-amazon.com/images/I/81xlbMTiLwL._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "The Hero of Ages", Medium.BOOK, Genre.FANTASY, 2008, "Cover of The Hero of Ages.", "https://m.media-amazon.com/images/I/81BFn0zMJ+L.jpg");
        create(itemService, "The Name of the Wind", Medium.BOOK, Genre.FANTASY, 2007, "Cover of The Name of the Wind.", "https://m.media-amazon.com/images/I/611iKJa7a-L.jpg");
        create(itemService, "The Wise Man's Fear", Medium.BOOK, Genre.FANTASY, 2011, "Cover of The Wise Man's Fear.", "https://m.media-amazon.com/images/I/51i16oSxg2L._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "1984", Medium.BOOK, Genre.OTHER, 1949, "Cover of 1984.", "https://mir-s3-cdn-cf.behance.net/project_modules/1400/b468d093312907.5e6139cf2ab03.png");
        create(itemService, "Animal Farm", Medium.BOOK, Genre.OTHER, 1945, "Cover of Animal Farm.", "https://m.media-amazon.com/images/I/91Lbhwt5RzL.jpg");
        create(itemService, "Dune", Medium.BOOK, Genre.FANTASY, 1965, "Cover of Dune.", "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1555447414i/44767458.jpg");
        create(itemService, "The Catcher in the Rye", Medium.BOOK, Genre.OTHER, 1951, "Cover of The Catcher in the Rye.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdT--yo-V-rOqePXZCrl8Sua6f7135CyohZeVE8sdYDKg2dopxSYKsS1EC&s=10");
        create(itemService, "To Kill a Mockingbird", Medium.BOOK, Genre.CRIME, 1960, "Cover of To Kill a Mockingbird.", "https://m.media-amazon.com/images/I/81O7u0dGaWL._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "The Silent Patient", Medium.BOOK, Genre.THRILLER, 2019, "Cover of The Silent Patient.", "https://m.media-amazon.com/images/S/compressed.photo.goodreads.com/books/1668782119i/40097951.jpg");
        create(itemService, "Project Hail Mary", Medium.BOOK, Genre.OTHER, 2021, "Cover of Project Hail Mary.", "https://m.media-amazon.com/images/I/916k6hSv0pL._AC_UF1000,1000_QL80_.jpg");
        create(itemService, "The Martian", Medium.BOOK, Genre.OTHER, 2011, "Cover of The Martian.", "https://static01.nyt.com/images/2014/02/05/books/05before-and-after-slide-T6H2/05before-and-after-slide-T6H2-superJumbo.jpg?quality=75&auto=webp&disable=upscale");
        create(itemService, "The Way of Kings", Medium.BOOK, Genre.FANTASY, 2010, "Cover of The Way of Kings.", "https://m.media-amazon.com/images/I/71AcXT6kTBL._AC_UF894,1000_QL80_.jpg");
        create(itemService, "Words of Radiance", Medium.BOOK, Genre.FANTASY, 2014, "Cover of Words of Radiance.", "https://m.media-amazon.com/images/I/71imlK2NtzL._AC_UF894,1000_QL80_.jpg");
        create(itemService, "The Shining", Medium.BOOK, Genre.HORROR, 1977, "Cover of The Shining.", "https://m.media-amazon.com/images/I/91U7HNa2NQL._AC_UF1000,1000_QL80_.jpg");

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
