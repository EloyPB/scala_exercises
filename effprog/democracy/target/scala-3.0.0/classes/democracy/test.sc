val votes = Seq("obama" -> "great", "obama" -> "great", "obama" -> "mediocre",
"trump" -> "bad", "trump" -> "mediocre")

votes.groupMap((c, g) => c)((c, g) => g)

votes.diff(Seq("obama" -> "great"))