package com.lay.codecpp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

/**
 * Kotlin shell for the Code C++ Android IDE.
 * C++ compilation is deliberately reported as unavailable until a real toolchain is connected.
 */
class MainActivity : Activity() {
    private val bg = Color.rgb(15, 18, 23)
    private val surface = Color.rgb(24, 29, 36)
    private val editorBg = Color.rgb(18, 22, 28)
    private val accent = Color.rgb(89, 204, 157)
    private val fg = Color.rgb(231, 237, 243)
    private val muted = Color.rgb(153, 165, 178)

    private lateinit var editor: EditText
    private lateinit var output: TextView
    private lateinit var fileLabel: TextView
    private lateinit var status: TextView
    private var fileName = "main.cpp"
    private var projectName = "MyCppProject"

    private val sample = "#include <iostream>\n\nint main() {\n    std::cout << \"Hello from Code C++!\" << std::endl;\n    return 0;\n}\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = bg
        window.navigationBarColor = bg
        buildWorkspace()
    }

    private fun dp(n: Int): Int = (n * resources.displayMetrics.density + 0.5f).toInt()

    private fun label(value: String, size: Float, color: Int = fg) =
        TextView(this).apply { text = value; textSize = size; setTextColor(color) }

    private fun button(value: String, action: () -> Unit) =
        Button(this).apply {
            text = value
            textSize = 12f
            isAllCaps = false
            setTextColor(fg)
            setOnClickListener { action() }
        }

    private fun bar() = LinearLayout(this).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        setPadding(dp(8), dp(3), dp(8), dp(3))
        setBackgroundColor(surface)
    }

    private fun buildWorkspace() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(bg)
            fitsSystemWindows = true
        }

        val top = bar()
        val brand = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        brand.addView(label("CODE C++", 18f, accent))
        brand.addView(label("Android development IDE", 10f, muted))
        top.addView(brand, LinearLayout.LayoutParams(0, -2, 1f))
        top.addView(button("+ Project") { newProject() })
        root.addView(top)

        val projectBar = bar()
        projectBar.addView(label("▣  " + projectName, 13f), LinearLayout.LayoutParams(0, -2, 1f))
        projectBar.addView(button("Open") { openFile() })
        projectBar.addView(button("Save") { saveFile() })
        root.addView(projectBar)

        val tab = bar()
        fileLabel = label("●  " + fileName, 13f, accent)
        tab.addView(fileLabel, LinearLayout.LayoutParams(0, -2, 1f))
        tab.addView(label("C++17", 11f, muted))
        root.addView(tab)

        editor = EditText(this).apply {
            setText(sample)
            setTextColor(Color.rgb(213, 226, 221))
            setHintTextColor(muted)
            textSize = 14f
            typeface = Typeface.MONOSPACE
            gravity = Gravity.TOP or Gravity.START
            setPadding(dp(14), dp(14), dp(14), dp(18))
            setBackgroundColor(editorBg)
            setHorizontallyScrolling(true)
            isSingleLine = false
            minLines = 14
            hint = "// Escreve o teu código C++ aqui"
        }
        val scroll = ScrollView(this).apply {
            isFillViewport = true
            addView(editor, ViewGroup.LayoutParams(-1, -2))
        }
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        val actions = bar()
        actions.addView(button("▶ Build") { showBuildStatus() })
        actions.addView(button("Terminal") { showTerminal() })
        actions.addView(button("Templates") { showTemplates() })
        actions.addView(button("Info") { showInfo() })
        root.addView(actions)

        status = label("● IDE pronta · compilador C++ ainda não ligado", 11f, accent)
        status.setPadding(dp(12), dp(8), dp(12), dp(4))
        root.addView(status)

        output = label("Bem-vindo ao Code C++.\nEscreve C++, abre um ficheiro ou guarda o teu código.", 12f, muted).apply {
            setPadding(dp(12), dp(8), dp(12), dp(12))
            setBackgroundColor(Color.rgb(10, 13, 17))
            minHeight = dp(74)
        }
        root.addView(output)
        setContentView(root)
    }

    private fun showBuildStatus() {
        output.text = "BUILD — backend ainda não configurado\n\n" +
            "O editor está pronto, mas Clang/LLVM e o Android linker ainda não estão ligados.\n" +
            "Nenhum APK do projeto C++ foi criado. Esta versão não declara sucesso falso."
        status.text = "● Editor pronto; compilador não configurado"
        status.setTextColor(Color.rgb(245, 190, 96))
    }

    private fun showTerminal() {
        output.text = "TERMINAL INTEGRADO\n\nA interface está preparada, mas uma shell real ainda não foi ligada. " +
            "A execução de processos será adicionada depois de escolher e testar uma toolchain compatível com Android."
    }

    private fun showTemplates() {
        output.text = "MODELOS C++ PLANEADOS\n\n" +
            "• Android NativeActivity\n• Aplicação gráfica/jogo em C++\n• Biblioteca nativa Android\n\n" +
            "Os modelos serão gerados como ficheiros-fonte; a compilação depende da toolchain."
    }

    private fun showInfo() {
        output.text = "CODE C++ · versão 0.1.0\n\n" +
            "IDE: Kotlin\nLinguagem dos projetos: C++\nAlvos: telemóveis e tablets Android\n" +
            "Compilação de projetos: em desenvolvimento"
    }

    private fun newProject() {
        fileName = "main.cpp"
        projectName = "MyCppProject"
        fileLabel.text = "●  " + fileName
        editor.setText(sample)
        output.text = "Modelo C++ inicial carregado. Usa Save para exportar o ficheiro."
        status.text = "● Modelo carregado · ainda não compilado"
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("text/plain", "text/x-c++src", "text/x-c++hdr", "application/octet-stream"))
        }
        @Suppress("DEPRECATION")
        startActivityForResult(intent, OPEN_FILE)
    }

    private fun saveFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"
            putExtra(Intent.EXTRA_TITLE, fileName)
        }
        @Suppress("DEPRECATION")
        startActivityForResult(intent, SAVE_FILE)
    }

    @Deprecated("Document picker callback retained for broad Android compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        val uri = data?.data ?: return
        try {
            if (requestCode == OPEN_FILE) {
                val source = contentResolver.openInputStream(uri)?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }.orEmpty()
                editor.setText(source)
                fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "source.cpp"
                fileLabel.text = "●  " + fileName
                output.text = "Ficheiro aberto. Revê e guarda as alterações quando necessário."
                status.text = "● Ficheiro aberto · não compilado"
            } else if (requestCode == SAVE_FILE) {
                val stream = contentResolver.openOutputStream(uri) ?: error("Não foi possível abrir o destino para escrita.")
                stream.bufferedWriter(Charsets.UTF_8).use { it.write(editor.text.toString()) }
                output.text = "Ficheiro guardado com sucesso."
                status.text = "● Ficheiro guardado"
            }
        } catch (e: Exception) {
            output.text = "Erro ao aceder ao ficheiro: " + (e.message ?: "erro desconhecido")
            Toast.makeText(this, "Não foi possível concluir a operação.", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val OPEN_FILE = 2101
        private const val SAVE_FILE = 2102
    }
}
