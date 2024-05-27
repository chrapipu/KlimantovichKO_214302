import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.CategoryCont;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryCont.class)
public class CategoryContTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepo categoryRepo;

    @Test
    public void testCategory() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Electronics"),
                new Category(2L, "Books")
        );
        when(categoryRepo.findAll()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attribute("categories", hasSize(2)))
                .andExpect(view().name("category"));
    }

    @Test
    public void testAddCategory() throws Exception {
        mockMvc.perform(post("/category/add").param("name", "Toys"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category"));

        verify(categoryRepo).save(any(Category.class));
    }

    @Test
    public void testEditCategory() throws Exception {
        Category existingCategory = new Category(1L, "Electronics");
        when(categoryRepo.getReferenceById(1L)).thenReturn(existingCategory);

        mockMvc.perform(post("/category/1/edit").param("name", "Gadgets"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category"));

        verify(categoryRepo).save(existingCategory);
        assertEquals("Gadgets", existingCategory.getName());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(get("/category/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/category"));

        verify(categoryRepo).deleteById(1L);
    }
}
