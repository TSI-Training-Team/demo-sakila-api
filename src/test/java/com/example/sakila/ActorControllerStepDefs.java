package com.example.sakila;

import com.example.sakila.controllers.ActorController;
import com.example.sakila.entities.Actor;
import com.example.sakila.input.ActorInput;
import com.example.sakila.output.ActorDetailsOutput;
import com.example.sakila.output.ActorReferenceOutput;
import com.example.sakila.output.PagedOutput;
import com.example.sakila.services.ActorService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ActorControllerStepDefs {

    private ActorService mockService;

    private ActorInput selectedInput;
    private Pageable pageable;
    private String queryName;
    private Integer queryPage;
    private Integer querySize;

    private ResponseEntity<ActorDetailsOutput> actualPostOutput;
    private ActorDetailsOutput actualActorDetails;
    private PagedOutput<ActorReferenceOutput> actualActorPagedOutput;
    private Exception caughtException;

    private List<Actor> expectedActors;
    private Page<Actor> expectedPage;

    private final Short expectedId = 42;
    private final String expectedFirstName = "JO";
    private final String expectedLastName = "BLOGGS";
    private final String expectedFullName = expectedFirstName + " " + expectedLastName;

    private final ActorInput validInput = new ActorInput(expectedFirstName, expectedLastName);

    private final Actor expectedActor = new Actor(
            expectedId,
            expectedFirstName,
            expectedLastName,
            expectedFullName,
            new ArrayList<>()
    );

    private final ActorDetailsOutput expectedActorDetails = new ActorDetailsOutput(
            expectedId,
            expectedFirstName,
            expectedLastName,
            expectedFullName,
            new ArrayList<>()
    );

    @Before
    public void setup() {
        mockService = mock(ActorService.class);
    }

    @Given("all valid Actor fields are in the request body")
    public void givenAllValidFields() {
        selectedInput = validInput;
        doReturn(expectedActor).when(mockService).createActor(validInput);
    }

    @Given("an actor exists with ID {short}")
    public void givenActor42Exists(Short id) {
        doReturn(expectedActor).when(mockService).findActor(id);
    }

    @Given("{int} actors exist")
    public void givenSomeActorsExist(Integer count) {
        expectedActors = IntStream.range(0, count)
                .boxed()
                .map(i -> new Actor(i.shortValue(), expectedFirstName, expectedLastName, expectedFullName, new ArrayList<>()))
                .toList();
    }

    @Given("no query params are passed")
    public void givenNoParams() {
        queryName = null;
        queryPage = null;
        querySize = null;
    }

    @Given("the 'name' query param is {string}")
    public void givenNameParam(String name) {
        queryName = name;
    }

    @Given("the 'page' query param is {int}")
    public void givenPageParam(Integer page) {
        queryPage = page;
    }

    @Given("the 'size' query param is {int}")
    public void givenSizeParam(Integer size) {
        querySize = size;
    }

    @When("a POST request is sent to \\/actors")
    public void whenPostRequest() {
        final var controller = new ActorController(mockService);
        try {
            actualPostOutput = controller.createActor(selectedInput);
            if (actualPostOutput != null) {
                actualActorDetails = actualPostOutput.getBody();
            }
        } catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a GET request is made to \\/actors\\/{short}")
    public void whenGetRequestWithId(Short id) {
        final var controller = new ActorController(mockService);
        try {
            actualActorDetails = controller.findActor(id);
        } catch (Exception ex) {
            caughtException = ex;
        }
    }

    @When("a GET request is made to \\/actors")
    public void whenGetRequestToCollection() {
        final var pageSize = Optional.ofNullable(querySize).orElse(50);
        final var pageNumber = Optional.ofNullable(queryPage).map(p -> p - 1).orElse(0);
        pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
        expectedPage = new PageImpl<>(
                expectedActors.subList(0, Math.min(pageSize, expectedActors.size())),
                pageable, expectedActors.size());
        doReturn(expectedPage).when(mockService).listActors(any());

        final var controller = new ActorController(mockService);
        try {
            actualActorPagedOutput = controller.listActors(
                    Optional.ofNullable(queryName),
                    Optional.ofNullable(queryPage),
                    Optional.ofNullable(querySize)
            );
        } catch (Exception ex) {
            caughtException = ex;
        }
    }

    @Then("an ActorDetailsOutput is returned")
    public void thenActorDetailsReturned() {
        assertNull(caughtException);
        assertNotNull(actualActorDetails);
    }

    @Then("the actor details are valid")
    public void thenActorDetailsValid() {
        assertEquals(expectedActorDetails, actualActorDetails);
    }

    @Then("the status code is 204 Created")
    public void thenStatus204() {
        assertEquals(HttpStatus.CREATED, actualPostOutput.getStatusCode());
    }

    @Then("a validation error is thrown")
    public void thenValidationError() {
        assertNotNull(caughtException);
        assertEquals(MethodArgumentNotValidException.class, caughtException.getClass());
    }

    @Then("a page of actors is returned")
    public void thenPageOfActors() {
        assertNotNull(actualActorPagedOutput);
    }

    @Then("the page number is {int}")
    public void thenPageNumberIs(Integer expectedPage) {
        final var pageInfo = actualActorPagedOutput.getPage();
        assertEquals(expectedPage, pageInfo.getNumber());
    }

    @Then("there is/are {long} actor(s) in the page")
    public void thenActorCountIs(long expectedItemCount) {
        final var pageInfo = actualActorPagedOutput.getPage();
        assertEquals(expectedItemCount, (long)pageInfo.getItemCount());
        assertEquals(expectedItemCount, actualActorPagedOutput.getItems().size());
    }

    @Then("there is/are {int} page(s) in total")
    public void thenPageCountIs(Integer expectedPageCount) {
        final var pageInfo = actualActorPagedOutput.getPage();
        assertEquals(expectedPageCount, pageInfo.getPageCount());
    }
}
