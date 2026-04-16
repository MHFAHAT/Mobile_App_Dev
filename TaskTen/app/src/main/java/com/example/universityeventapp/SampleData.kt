package com.example.universityeventapp

object SampleData {
    val events = listOf(
        Event(
            1, "Tech Summit 2024", "Oct 15, 2024", "10:00 AM", "Main Auditorium", "Tech",
            "Join us for the biggest tech conference of the year featuring industry leaders.",
            50.0, 48, 30, R.drawable.ic_launcher_background
        ),
        Event(
            2, "Annual Sports Meet", "Oct 20, 2024", "08:00 AM", "University Ground", "Sports",
            "Compete in various sports and show your athletic skills.",
            0.0, 48, 40, R.drawable.ic_launcher_background
        ),
        Event(
            3, "Cultural Fest", "Oct 25, 2024", "05:00 PM", "Open Air Theater", "Cultural",
            "A night of music, dance, and drama showcasing our diverse culture.",
            20.0, 48, 15, R.drawable.ic_launcher_background
        ),
        Event(
            4, "Research Symposium", "Nov 02, 2024", "09:00 AM", "Conference Hall", "Academic",
            "Present your research papers and learn from eminent scholars.",
            10.0, 48, 45, R.drawable.ic_launcher_background
        ),
        Event(
            5, "Coding Contest", "Nov 10, 2024", "11:00 AM", "Lab 101", "Tech",
            "Showcase your coding skills in this competitive programming challenge.",
            5.0, 48, 20, R.drawable.ic_launcher_background
        ),
        Event(
            6, "Charity Marathon", "Nov 15, 2024", "06:00 AM", "City Park", "Social",
            "Run for a cause. All proceeds go to local charities.",
            15.0, 48, 48, R.drawable.ic_launcher_background
        ),
        Event(
            7, "Art Exhibition", "Nov 20, 2024", "10:00 AM", "Art Gallery", "Cultural",
            "Explore the creative works of our talented students.",
            0.0, 48, 35, R.drawable.ic_launcher_background
        ),
        Event(
            8, "Business Workshop", "Dec 05, 2024", "02:00 PM", "Seminar Room", "Academic",
            "Learn the basics of entrepreneurship and business management.",
            25.0, 48, 10, R.drawable.ic_launcher_background
        )
    )
}
