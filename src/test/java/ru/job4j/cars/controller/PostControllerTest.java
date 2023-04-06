package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.util.HttpHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    void whenPostsWithStatusNull() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            page = postController.posts(model, httpSession, null);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
            verify(postService).findAll(HttpHelper.getCurrentUser(httpSession));
        }
        assertThat(page).isEqualTo("/post/posts");
    }

    @Test
    void whenPostsWithStatusTrue() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            Boolean status = true;
            page = postController.posts(model, httpSession, status);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
            verify(postService).findByStatus(status);
        }
        assertThat(page).isEqualTo("/post/posts");
    }

    @Test
    void whenPostsWithStatusFalse() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            Boolean status = false;
            page = postController.posts(model, httpSession, status);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
            verify(postService).findByStatus(status);
        }
        assertThat(page).isEqualTo("/post/posts");
    }

    @Test
    void whenAddThenReturnPage() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            page = postController.add(model, httpSession);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
        }
        assertThat(page).isEqualTo("/post/add");
    }

    @Test
    void whenCreateThenSuccess() throws Exception {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        String page = postController.create(req, httpSession, attr);
        verify(postService).createPostFromRequestParam(req, httpSession);
        assertThat(page).isEqualTo("redirect:/post/posts");
    }

    @Test
    void whenCreateThenIllegalStateExceptionHandle() throws Exception {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        Exception ex = new IllegalStateException();
        when(postService.createPostFromRequestParam(req, httpSession)).thenThrow(ex);
        String page = postController.create(req, httpSession, attr);
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenCreateThenNumberFormatExceptionHandle() throws Exception {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        Exception ex = new NumberFormatException();
        when(postService.createPostFromRequestParam(req, httpSession)).thenThrow(ex);
        String page = postController.create(req, httpSession, attr);
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenCreateThenExceptionHandle() throws Exception {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        when(postService.createPostFromRequestParam(req, httpSession)).thenThrow(new Exception());
        String page = postController.create(req, httpSession, attr);
        verify(attr).addFlashAttribute("error_message", "При сохранении данных произошла ошибка");
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenSelectThenSuccess() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        int postId = 1;
        Post post = new Post();
        when(postService.findById(postId)).thenReturn(post);
        String page = postController.select(postId, attr);
        verify(attr).addFlashAttribute("post", post);
        assertThat(page).isEqualTo("redirect:/post/poster");
    }

    @Test
    void whenSelectThenExceptionHandle() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        int postId = 1;
        Exception ex = new NoSuchElementException();
        when(postService.findById(postId)).thenThrow(ex);
        String page = postController.select(postId, attr);
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenSelectThenReturnPosterPage() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            page = postController.select(model, httpSession);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
        }
        assertThat(page).isEqualTo("/post/poster");
    }

    @Test
    void whenUpdateThenReturnPostsPage() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        HttpSession httpSession = mock(HttpSession.class);
        Post post = new Post();
        MultipartFile file = mock(MultipartFile.class);
        String page = postController.update(post, attr, httpSession, file);
        verify(postService).update(post, httpSession);
        assertThat(page).isEqualTo("redirect:/post/posts");
    }

    @Test
    void whenUpdateThenIllegalArgumentExceptionHandle() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        HttpSession httpSession = mock(HttpSession.class);
        Post post = new Post();
        Exception ex = new IllegalArgumentException();
        doThrow(ex).when(postService).update(post, httpSession);
        MultipartFile file = mock(MultipartFile.class);
        String page = postController.update(post, attr, httpSession, file);
        verify(postService).update(post, httpSession);
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenUpdateThenExceptionHandle() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        HttpSession httpSession = mock(HttpSession.class);
        Post post = new Post();
        doAnswer((invocation) -> {
            throw new Exception();
        }).when(postService).update(post, httpSession);
        MultipartFile file = mock(MultipartFile.class);
        String page = postController.update(post, attr, httpSession, file);
        verify(postService).update(post, httpSession);
        verify(attr).addFlashAttribute("error_message", "При обновлении данных произошла ошибка");
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenUpdateThenReturnUpdatePage() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        HttpSession httpSession = mock(HttpSession.class);
        int id = 1;
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            page = postController.update(id, model, httpSession, attr);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
        }
        verify(model).addAttribute("post", postService.findById(id));
        assertThat(page).isEqualTo("/post/update");
    }

    @Test
    void whenUpdateThenExceptionHandleAndReturnErrorPage() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        Model model = mock(Model.class);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        HttpSession httpSession = mock(HttpSession.class);
        Exception ex = new NoSuchElementException();
        int id = 1;
        doThrow(ex).when(postService).findById(id);
        String page;
        try (MockedStatic<HttpHelper> httpHelper = mockStatic(HttpHelper.class)) {
            page = postController.update(id, model, httpSession, attr);
            httpHelper.verify(() -> HttpHelper.addUserToModel(model, httpSession));
        }
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }

    @Test
    void whenGetPostThenReturnRedirect() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        int id = 1;
        String page = postController.getPost(id, attr);
        verify(attr).addFlashAttribute("post", postService.findById(id));
        assertThat(page).isEqualTo("redirect:/post/photo/resource");
    }

    @Test
    void whenGetPostThenNoSouchElementExceptionHandle() {
        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        int id = 1;
        Exception ex = new NoSuchElementException();
        doThrow(ex).when(postService).findById(id);
        String page = postController.getPost(id, attr);
        verify(attr).addFlashAttribute("error_message", ex.getMessage());
        assertThat(page).isEqualTo("redirect:/error/fail");
    }
}