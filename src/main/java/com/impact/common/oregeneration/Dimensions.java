package com.impact.common.oregeneration;

public enum Dimensions {
    NO(-1, -1, ""),

    Overworld(0, 0, "Overworld"),

    Moon(-28, 1, "Moon"),

    Mars(-29, 2, "Mars"), Deimos(-2053, 2, "Deimos"), Phobos(-2052, 2, "Phobos"),

    Asteroids(-30, 3, "Asteroids"), Ceres(-2002, 3, "Ceres"), Ganymede(-2011, 3, "Ganymede"), Callisto(-2017, 3, "Callisto"), Europa(-2010, 3, "Europa"),

    Venus(-2001, 4, "Venus"), Mercury(-2000, 4, "Mercury"), Io(-2009, 4, "Io"),

    Miranda(-2019, 5, "Miranda"), Enceladus(-2012, 5, "Enceladus"), Titan(-2013, 5, "Titan"), Oberon(-2056, 5, "Oberon"),

    Proteus(-2055, 6, "Proteus"), Triton(-2016, 6, "Triton"),

    Makemake(-2050, 7, "Makemake"), Pluto(-2003, 7, "Pluto"), Haumea(-2051, 7, "Haumea"), KuiperBelt(-2004, 7, "Kuiper Belt");

    public int id;
    public int tier;
    public String name;

    Dimensions(int id, int tier, String name) {
        this.id = id;
        this.tier = tier;
        this.name = name;
    }
}
