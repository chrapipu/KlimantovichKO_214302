import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.ProductManagementController;
import com.example.demo.model.Category;
import com.example.demo.model.Products;
import com.example.demo.model.ProductStatus;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductManagementController.class)
public class ProductManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productsRepo;

    @MockBean
    private CategoryRepository categoryRepo;

    @Test
    public void testProductEditForm() throws Exception {
        Long productId = 1L;
        Products product = new Products(productId, "Laptop", 10, 1000, 10, "path", new Category(1L, "Electronics"));
        when(productsRepo.getReferenceById(productId)).thenReturn(product);

        mockMvc.perform(get("/product/edit/" + productId))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("name", is("Laptop"))))
                .andExpect(view().name("product_edit"));
    }

    @Test
    public void testProductDelete() throws Exception {
        Long productId = 1L;

        mockMvc.perform(get("/product/delete/" + productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productsRepo).deleteById(productId);
    }

    @Test
    public void testProductArchive() throws Exception {
        Long productId = 1L;
        Products product = new Products(productId, "Laptop", 10, 1000, 10, "path", new Category(1L, "Electronics"));
        when(productsRepo.getReferenceById(productId)).thenReturn(product);

        mockMvc.perform(get("/product/archive/" + productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productsRepo).save(product);
        assertEquals(ProductStatus.ARCHIVE, product.getStatus());
    }

    @Test
    public void testProductAdd() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        Long categoryId = 1L;

        mockMvc.perform(multipart("/product/add")
                        .file(file)
                        .param("name", "Smartphone")
                        .param("categoryId", categoryId.toString())
                        .param("count", "20")
                        .param("price", "500")
                        .param("discount", "15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productsRepo, times(1)).save(any(Products.class));
    }

    @Test
    public void testProductEditWithFile() throws Exception {
        Long productId = 1L;
        Products existingProduct = new Products(productId, "OldName", 10, 1000, 10, "oldpath", new Category(1L, "Electronics"));
        MockMultipartFile file = new MockMultipartFile("file", "newfile.txt", "text/plain", "new content".getBytes());
        when(productsRepo.getReferenceById(productId)).thenReturn(existingProduct);

        mockMvc.perform(multipart("/product/edit/" + productId)
                        .file(file)
                        .param("name", "NewName")
                        .param("categoryId", "1")
                        .param("count", "15")
                        .param("price", "1100")
                        .param("discount", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productsRepo).save(existingProduct);
        assertEquals("NewName", existingProduct.getName());
        assertEquals(15, existingProduct.getCount());
        assertEquals(1100, existingProduct.getPrice());
        assertEquals(5, existingProduct.getDiscount());
        assertNotNull(existingProduct.getFile());
        assertTrue(existingProduct.getFile().contains("newfile.txt"));
    }

    @Test
    public void testProductEditWithoutFile() throws Exception {
        Long productId = 1L;
        Products existingProduct = new Products(productId, "OldName", 10, 1000, 10, "oldpath", new Category(1L, "Electronics"));
        when(productsRepo.getReferenceById(productId)).thenReturn(existingProduct);

        mockMvc.perform(post("/product/edit/" + productId)
                        .param("name", "UpdatedName")
                        .param("categoryId", "1")
                        .param("count", "20")
                        .param("price", "1200")
                        .param("discount", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));

        verify(productsRepo).save(existingProduct);
        assertEquals("UpdatedName", existingProduct.getName());
        assertEquals(20, existingProduct.getCount());
        assertEquals(1200, existingProduct.getPrice());
        assertEquals(20, existingProduct.getDiscount());
        assertEquals("oldpath", existingProduct.getFile());
    }

    @Test
    public void testProductEditWithErrorDuringFileUpload() throws Exception {
        Long productId = 1L;
        Products existingProduct = new Products(productId, "OldName", 10, 1000, 10, "oldpath", new Category(1L, "Electronics"));
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "content".getBytes());
        when(productsRepo.getReferenceById(productId)).thenReturn(existingProduct);

        doThrow(new IOException("Fake IOException")).when(file).transferTo(any(File.class));

        mockMvc.perform(multipart("/product/edit/" + productId)
                        .file(file)
                        .param("name", "NewName")
                        .param("categoryId", "1")
                        .param("count", "15")
                        .param("price", "1100")
                        .param("discount", "5"))
                .andExpect(status().isOk()) // Assuming the view handles displaying the error message
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "Слишком большой размер аватарки"))
                .andExpect(view().name("product_edit"));

        verify(productsRepo, never()).save(any(Products.class));
    }
}
