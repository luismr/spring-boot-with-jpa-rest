package com.example.simplejparest;

import com.example.simplejparest.data.World;
import com.example.simplejparest.repository.WorldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class WorldRepositoryTest {

    @Autowired
    private WorldRepository worldRepository;

    private Iterable<World> worlds;

    @BeforeEach
    public void setupEach() {
        this.worlds = worldRepository.findAll();
    }

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(worldRepository).isNotNull();
        assertThat(worlds).isNotNull();
    }

    @Test
    public void checkIfThereIs8planets() {
        assertThat(worlds).size().isEqualTo(8);
    }

    @Test
    public void checkIfThereIsSomePlanetEarth() {
        List<World> worldList = new ArrayList<>();
        worlds.forEach(worldList::add);
        Optional<World> found = worldList.stream().filter(w -> "Earth".equalsIgnoreCase(w.getName())).findFirst();
        assertThat(found.get()).isNotNull();
    }

    @Test
    public void checkIfThereIsSomeInvalidPlanet() {
        List<World> worldList = new ArrayList<>();
        worlds.forEach(worldList::add);
        Optional<World> found = worldList.stream().filter(w -> "Pluto".equalsIgnoreCase(w.getName())).findFirst();
        assertThat(found.isPresent()).isFalse();
    }
}
