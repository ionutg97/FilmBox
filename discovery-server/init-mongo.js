db.createUser(
	{
		user:"Jhon",
		roles:[
			{
				role : "read",
				db : "movie"
			}
		]
	}
)