package com.example.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.example.R

@Immutable
data class King(
    val id: String,
    val shortName: String,
    val fullName: String,
    val reignEra: String,
    val eraYears: String,
    val birthplace: String,
    val biography: String,
    val achievements: List<String>,
    @DrawableRes val portraitResId: Int,
)

object KingsRepository {
    val allKings: List<King> = listOf(
        King(
            id = "tewodros",
            shortName = "አፄ ቴዎድሮስ ፪ኛ",
            fullName = "አፄ ቴዎድሮስ ፪ኛ (ካሣ ኃይሉ)",
            reignEra = "ዘመነ መንግሥት፡ 1847 - 1860 ዓ.ም.",
            eraYears = "1847 – 1860 ዓ.ም.",
            birthplace = "ቋራ፣ ጎንደር",
            biography = "አፄ ቴዎድሮስ በቋራ የተወለዱ ሲሆን፣ ኢትዮጵያን ለዘመናት ከፋፍሎ የነበረውን የ«ዘመነ መሳፍንት» ሥርዓት በማስወገድ ዘመናዊቷንና አንድነቷ የተጠበቀ ኢትዮጵያን ለመገንባት የመጀመሪያውን መሠረት የጣሉ ታላቅ ንጉሥ ናቸው።",
            achievements = listOf(
                "ዘመነ መሳፍንትን አብቅተው ማዕከላዊ መንግሥትን መሠረቱ።",
                "ደመወዝ የሚከፈለው ዘመናዊ ብሔራዊ ሠራዊት አደራጁ።",
                "በጋፋት (ደብረ ታቦር) የመጀመሪያውን የሀገር ውስጥ የጦር መሣሪያ (ሴባስቶፖል መድፍ) አሠሩ።",
                "ለሀገር ክብርና ነፃነት ሲሉ በመቅደላ እጅ ላለመስጠት ራሳቸውን ሰዉ።",
            ),
            portraitResId = R.drawable.img_king_tewodros_1794639974000,
        ),
        King(
            id = "yohannes",
            shortName = "አፄ ዮሐንስ ፬ኛ",
            fullName = "አፄ ዮሐንስ ፬ኛ (በዝብዝ ካሣ)",
            reignEra = "ዘመነ መንግሥት፡ 1864 - 1881 ዓ.ም.",
            eraYears = "1864 – 1881 ዓ.ም.",
            birthplace = "ተምቤን፣ ትግራይ",
            biography = "አፄ ዮሐንስ ፬ኛ የኢትዮጵያን ዳር ድንበር ከውጭ ወራሪዎች (ከግብፅ፣ ከጣሊያን እና ከደርቡሾች) በመከላከል ረገድ ወደር የሌለው ጀግንነት የፈጸሙ ንጉሠ ነገሥት ናቸው።",
            achievements = listOf(
                "በጉንደት እና በጉራዕ የግብፅን ወራሪ ሠራዊት አሸነፉ።",
                "በዶጋሊ የጣሊያንን ወራሪ ኃይል ድል አደረጉ።",
                "በመተማ ጦርነት ለሀገራቸው ዳር ድንበር ሲፋለሙ በጀግንነት ወደቁ።",
            ),
            portraitResId = R.drawable.img_king_yohannes_1794639975965,
        ),
        King(
            id = "menelik",
            shortName = "አፄ ምኒልክ ፪ኛ",
            fullName = "ዳግማዊ አፄ ምኒልክ",
            reignEra = "ዘመነ መንግሥት፡ 1882 - 1906 ዓ.ም.",
            eraYears = "1882 – 1906 ዓ.ም.",
            birthplace = "አንጎለላ፣ ሸዋ",
            biography = "ዳግማዊ አፄ ምኒልክ የዘመናዊቷ ኢትዮጵያ መሥራችና በዓድዋ ድል የጥቁር ሕዝቦችን የነፃነት ችቦ የለኮሱ ታላቅ መሪ ናቸው።",
            achievements = listOf(
                "በ1888 ዓ.ም. በዓድዋ ጦርነት የጣሊያንን ቅኝ ገዢ ሠራዊት ድል አደረጉ።",
                "ዘመናዊ ትምህርት ቤት (ዳግማዊ ምኒልክ)፣ ሆስፒታል፣ ባንክ፣ ፖስታ እና ባቡር ወደ ሀገር ውስጥ አስገቡ።",
                "አዲስ አበባን የኢትዮጵያ ዋና ከተማ አድርገው ቆረቆሩ።",
            ),
            portraitResId = R.drawable.img_king_menelik_1794639976706,
        ),
        King(
            id = "haile",
            shortName = "ቀዳማዊ ኃይለ ሥላሴ",
            fullName = "ቀዳማዊ ኃይለ ሥላሴ",
            reignEra = "ዘመነ መንግሥት፡ 1923 - 1967 ዓ.ም.",
            eraYears = "1923 – 1967 ዓ.ም.",
            birthplace = "ኤጀርሳ ጎሮ፣ ሐረርጌ",
            biography = "ቀዳማዊ ኃይለ ሥላሴ ኢትዮጵያን በዓለም አቀፍ መድረክ በማስተዋወቅ፣ ዘመናዊ ሕገ-መንግሥት በማርቀቅ እና የአፍሪካ አንድነት ድርጅት (OAU) እንዲመሠረት ከፍተኛ ሚና የተጫወቱ ንጉሥ ናቸው።",
            achievements = listOf(
                "የመጀመሪያውን የኢትዮጵያ ጽሑፍ ሕገ-መንግሥት በ1923 ዓ.ም. አወጡ።",
                "ኢትዮጵያን የዓለም መንግሥታት ማኅበር እና የተባበሩት መንግሥታት ድርጅት አባል አደረጉ።",
                "የአፍሪካ አንድነት ድርጅት (የአሁኑ አፍሪካ ኅብረት) ዋና መሥሪያ ቤት አዲስ አበባ እንዲሆን አደረጉ።",
            ),
            portraitResId = R.drawable.img_king_haile_1794639975665,
        ),
    )

    fun findById(id: String?): King? = allKings.firstOrNull { it.id == id }
}
