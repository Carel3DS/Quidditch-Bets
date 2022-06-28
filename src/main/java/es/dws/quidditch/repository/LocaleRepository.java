package es.dws.quidditch.repository;

import es.dws.quidditch.model.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocaleRepository extends JpaRepository<Locale,Long> {
    Optional<Locale> findByName(String localeID);
    Locale getByName(String localeID);
}
